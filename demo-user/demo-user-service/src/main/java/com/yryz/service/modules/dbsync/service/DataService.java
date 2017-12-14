package com.yryz.service.modules.dbsync.service;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClientException;
import com.mongodb.MongoSocketException;
import com.yryz.service.modules.dbsync.common.NameConst;
import com.yryz.service.modules.dbsync.util.DBConvertUtil;
import com.yryz.service.modules.dbsync.util.SpringUtil;
import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 介绍
 *
 * @author zhangkun
 * @version 1.0
 * @date 2017/12/5
 * @description
 */
@Service
public class DataService {
    protected final static Logger logger = LoggerFactory.getLogger(DataService.class);

    @Autowired
    MongoTemplate naiveMongoTemplate;
    @Autowired
    MongoTemplate errorLogMongoTemplate;

    public void insert(List<CanalEntry.Column> data, String schemaName, String tableName) {
        DBObject obj = DBConvertUtil.columnToJson(data);
        logger.debug("insert ：{}", obj.toString());

        DBObject newObj = (DBObject) ObjectUtils.clone(obj);
        if (newObj.containsField("id")) {
            processTableId(newObj);
        }
        insertData(schemaName, tableName, newObj, obj);
    }

    private void processTableId(DBObject newObj) {
        //表中id自增字段处理 id => tid
        newObj.put("tid", newObj.get("id"));
        newObj.removeField("id");
    }


    public void delete(List<CanalEntry.Column> data, String schemaName, String tableName) {
        DBObject obj = DBConvertUtil.columnToJson(data);
        logger.debug("delete：{}", obj.toString());
        /*if (schemaName.equals("order")) {
            logger.info("not support delete：{}.{}:{}", schemaName, tableName, obj);
        } else {*/

        deleteData(schemaName, tableName, obj);
        //}
    }


    public void update(List<CanalEntry.Column> data, String schemaName, String tableName) {
        DBObject obj = DBConvertUtil.columnToJson(data);
        logger.debug("update：{}", obj.toString());
        //订单库单独处理
        /*if (schemaName.equals("order")) {
            if (tableName.startsWith("order_base_info")) {
                tableName = "order_base_info";
            } else if (tableName.startsWith("order_detail_info")) {
                tableName = "order_detail_info";
            } else {
                logger.info("unknown data：{}.{}:{}", schemaName, tableName, obj);
            }
            updateData(schemaName, tableName, new BasicDBObject("orderId", obj.get("orderId")), obj);
        } else {*/
        //根据kid字段更新
        if (obj.containsField("kid")) {
            updateData(schemaName, tableName, new BasicDBObject("kid", obj.get("kid")), obj);
        } else {
            logger.info("unknown data structure");
        }
//        }
    }

    public void insertData(String schemaName, String tableName, DBObject naive, DBObject complete) {
        int i = 0;
        DBObject logObj = (DBObject) ObjectUtils.clone(complete);
        //保存原始数据
        try {
            String path = "/" + schemaName + "/" + tableName + "/" + CanalEntry.EventType.INSERT.getNumber();
            i++;
            naiveMongoTemplate.getCollection(tableName).insert(naive);
            i++;
            SpringUtil.doEvent(path, complete);
            i++;
        } catch (MongoClientException | MongoSocketException clientException) {
            //客户端连接异常抛出，阻塞同步，防止mongodb宕机
            throw clientException;
        } catch (Exception e) {
            logError(schemaName, tableName, "INSERT", i, logObj, e);
        }
    }

    public void updateData(String schemaName, String tableName, DBObject query, DBObject obj) {
        String path = "/" + schemaName + "/" + tableName + "/" + CanalEntry.EventType.UPDATE.getNumber();
        int i = 0;
        DBObject newObj = (DBObject) ObjectUtils.clone(obj);
        DBObject logObj = (DBObject) ObjectUtils.clone(obj);
        //保存原始数据
        try {
            if (obj.containsField("id")) {
                processTableId(obj);
            }
            i++;
            naiveMongoTemplate.getCollection(tableName).update(query, obj);
            i++;
            SpringUtil.doEvent(path, newObj);
            i++;
        } catch (MongoClientException | MongoSocketException clientException) {
            //客户端连接异常抛出，阻塞同步，防止mongodb宕机
            throw clientException;
        } catch (Exception e) {
            logError(schemaName, tableName, "UPDATE", i, logObj, e);
        }
    }


    public void deleteData(String schemaName, String tableName, DBObject obj) {
        int i = 0;
        String path = "/" + schemaName + "/" + tableName + "/" + CanalEntry.EventType.DELETE.getNumber();
        DBObject newObj = (DBObject) ObjectUtils.clone(obj);
        DBObject logObj = (DBObject) ObjectUtils.clone(obj);
        //保存原始数据
        try {
            i++;
            if (obj.containsField("kid")) {
                naiveMongoTemplate.getCollection(tableName).remove(new BasicDBObject("kid", obj.get("kid")));
            }
            i++;
            SpringUtil.doEvent(path, newObj);
        } catch (MongoClientException | MongoSocketException clientException) {
            //客户端连接异常抛出，阻塞同步，防止mongodb宕机
            throw clientException;
        } catch (Exception e) {
            logError(schemaName, tableName, "DELETE", i, logObj, e);
        }
    }

    /**
     * 记录拼接表错误记录
     *
     * @param schemaName
     * @param tableName
     * @param event      1:INSERT;2:UPDATE;3:DELETE
     * @param step       0:预处理数据错误;1:保存原始数据错误;2:保存组合数据错误，
     * @param obj
     * @param e
     */
    private void logError(String schemaName, String tableName, String event, int step, DBObject obj, Exception e) {
        logger.error("error data：name[{},{}] , eventType : {} , data : [{}]", schemaName, tableName, event, obj);
        DBObject errObj = new BasicDBObject();
        errObj.put("schemaName", schemaName);
        errObj.put("tableName", tableName);
        errObj.put("event", event);
        errObj.put("data", obj);
        errObj.put("step", step);
        errObj.put("time", new Date());
        errObj.put("error", e.toString());
        errorLogMongoTemplate.getCollection(NameConst.C_ERROR_RECORD).insert(errObj);
    }


}
