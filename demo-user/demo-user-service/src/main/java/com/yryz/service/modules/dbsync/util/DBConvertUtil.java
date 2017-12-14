package com.yryz.service.modules.dbsync.util;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 数据转换类
 *
 * @author zhangkun
 * @version 1.0
 * @date 2017/12/5
 * @description
 */
public class DBConvertUtil {
    protected final static Logger logger = LoggerFactory.getLogger(DBConvertUtil.class);
    private static final DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * binlog行数据列转换成DBObject
     *
     * @param columns
     * @return
     */
    public static DBObject columnToJson(List<CanalEntry.Column> columns) {
        DBObject obj = new BasicDBObject();
        for (CanalEntry.Column column : columns) {
            String mysqlType = column.getMysqlType();
            Object value = dataTypeConvert(mysqlType, column.getValue());
            obj.put(column.getName(), value);
        }
        return obj;
    }

    /**
     * 数据类型转换
     *
     * @param mysqlType
     * @param value
     * @return
     */
    private static Object dataTypeConvert(String mysqlType, String value) {
        try {
            if (mysqlType.startsWith("int") || mysqlType.startsWith("tinyint") || mysqlType.startsWith("smallint") || mysqlType.startsWith("mediumint")) {
                //int(32)
                return StringUtils.isBlank(value) ? null : Integer.parseInt(value);
            } else if (mysqlType.startsWith("bigint")) {
                //int(64)
                return StringUtils.isBlank(value) ? null : Long.parseLong(value);
            } else if (mysqlType.startsWith("float") || mysqlType.startsWith("double")) {
                return StringUtils.isBlank(value) ? null : Double.parseDouble(value);
            } else if (mysqlType.startsWith("decimal")) {
                //小数精度为0时转换成long类型，否则转换为double类型
                int lenBegin = mysqlType.indexOf('(');
                int lenCenter = mysqlType.indexOf(',');
                int lenEnd = mysqlType.indexOf(')');
                if (lenBegin > 0 && lenEnd > 0 && lenCenter > 0) {
                    int length = Integer.parseInt(mysqlType.substring(lenCenter + 1, lenEnd));
                    if (length == 0) {
                        return StringUtils.isBlank(value) ? null : Long.parseLong(value);
                    }
                }
                return StringUtils.isBlank(value) ? null : Double.parseDouble(value);
            } else if (mysqlType.equals("datetime") || mysqlType.equals("timestamp")) {
                return StringUtils.isBlank(value) ? null : DATE_TIME_FORMAT.parse(value);
            } else if (mysqlType.equals("date")) {
                return StringUtils.isBlank(value) ? null : DATE_FORMAT.parse(value);
            }
        } catch (Exception e) {
            logger.error("data type convert error ", e);
        }
        return value;
    }
}
