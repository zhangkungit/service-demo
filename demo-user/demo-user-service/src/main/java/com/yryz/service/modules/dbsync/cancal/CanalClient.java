package com.yryz.service.modules.dbsync.cancal;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry.*;
import com.alibaba.otter.canal.protocol.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.yryz.service.modules.dbsync.service.DataService;
import com.yryz.service.modules.dbsync.util.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * canalClient 启动类
 *
 * @author zhangkun
 * @version 1.0
 * @date 2017/12/5
 * @description
 */

public class CanalClient {

    private final static Logger logger = LoggerFactory.getLogger(CanalClient.class);
    private volatile boolean running = false;
    private Thread.UncaughtExceptionHandler handler = new Thread.UncaughtExceptionHandler() {
        public void uncaughtException(Thread t, Throwable e) {
            logger.error("parse events has an error", e);
        }
    };
    private Thread thread = null;
    private CanalConnector connector;
    //行数据日志
    private static String row_format = "binlog[{}:{}] , name[{},{}] , eventType : {} , executeTime : {} , delay : {}ms";
    //事务日志
    private static String transaction_format = "binlog[{}:{}] , executeTime : {} , delay : {}ms";
    //数据存储耗时日志
    private static String execute_format = "binlog[{}:{}] , name[{},{}] , eventType : {} , rowCount: {} , consume : {}ms";

    private String destination;

    public CanalClient(String destination, CanalConnector connector) {
        this.destination = destination;
        this.connector = connector;
    }

    public void start() {
        Assert.notNull(connector, "connector is null");
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("destination:{} running", destination);
                process();
            }
        });
        thread.setUncaughtExceptionHandler(handler);
        thread.start();
        running = true;
    }

    public void stop() {
        if (!running) {
            return;
        }
        running = false;
        if (thread != null) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        MDC.remove("destination");
    }

    private void process() {
        int batchSize = 5 * 1024;
        while (running) {
            try {
                MDC.put("destination", destination);
                connector.connect();
                connector.subscribe();
                while (running) {
                    // 获取指定数量的数据
                    Message message = connector.getWithoutAck(batchSize);
                    long batchId = message.getId();
                    int size = message.getEntries().size();
                    if (batchId != -1 && size > 0) {
                        saveEntry(message.getEntries());
                    }
                    // 提交确认
                    connector.ack(batchId);
                    // connector.rollback(batchId); // 处理失败, 回滚数据
                }
            } catch (Exception e) {
                logger.error("process error!", e);
            } finally {
                connector.disconnect();
                MDC.remove("destination");
            }
        }
    }

    protected void saveEntry(List<Entry> entrys) {
        for (Entry entry : entrys) {
            long executeTime = entry.getHeader().getExecuteTime();
            long startTime = System.currentTimeMillis();
            long delayTime = startTime - executeTime;
            //打印事务开始结束信息
            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN) {
                    TransactionBegin begin = null;
                    try {
                        begin = TransactionBegin.parseFrom(entry.getStoreValue());
                    } catch (InvalidProtocolBufferException e) {
                        throw new RuntimeException("parse event has an error , data:" + entry.toString(), e);
                    }
                    // 打印事务头信息，执行的线程id，事务耗时
                    logger.info(transaction_format, entry.getHeader().getLogfileName(), String.valueOf(entry.getHeader().getLogfileOffset()),
                            String.valueOf(entry.getHeader().getExecuteTime()), String.valueOf(delayTime));
                    logger.info(" BEGIN ----> Thread id: {}", begin.getThreadId());
                } else if (entry.getEntryType() == EntryType.TRANSACTIONEND) {
                    TransactionEnd end = null;
                    try {
                        end = TransactionEnd.parseFrom(entry.getStoreValue());
                    } catch (InvalidProtocolBufferException e) {
                        throw new RuntimeException("parse event has an error , data:" + entry.toString(), e);
                    }
                    // 打印事务提交信息，事务id
                    logger.info(" END ----> transaction id: {}", end.getTransactionId());
                    logger.info(transaction_format, entry.getHeader().getLogfileName(), String.valueOf(entry.getHeader().getLogfileOffset()),
                            String.valueOf(entry.getHeader().getExecuteTime()), String.valueOf(delayTime));
                }
                continue;
            }
            //保存事务内变动数据
            if (entry.getEntryType() == EntryType.ROWDATA) {
                RowChange rowChage = null;
                try {
                    rowChage = RowChange.parseFrom(entry.getStoreValue());
                } catch (Exception e) {
                    throw new RuntimeException("parse event has an error , data:" + entry.toString(), e);
                }
                EventType eventType = rowChage.getEventType();
                logger.info(row_format, entry.getHeader().getLogfileName(), String.valueOf(entry.getHeader().getLogfileOffset()),
                        entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                        eventType, String.valueOf(entry.getHeader().getExecuteTime()), String.valueOf(delayTime));
                if (eventType == EventType.QUERY || rowChage.getIsDdl()) {
                    logger.info(" sql ----> " + rowChage.getSql());
                    continue;
                }
                DataService dataService = SpringUtil.getBean(DataService.class);
                for (RowData rowData : rowChage.getRowDatasList()) {
                    String tableName = entry.getHeader().getTableName();
                    String trueTableName = org.apache.commons.lang3.StringUtils.substring(tableName, 0, tableName.lastIndexOf("_"));

                    if (eventType == EventType.DELETE) {
                        dataService.delete(rowData.getBeforeColumnsList(), entry.getHeader().getSchemaName(), trueTableName);
                    } else if (eventType == EventType.INSERT) {
                        dataService.insert(rowData.getAfterColumnsList(), entry.getHeader().getSchemaName(), trueTableName);
                    } else if (eventType == EventType.UPDATE) {
                        dataService.update(rowData.getAfterColumnsList(), entry.getHeader().getSchemaName(), trueTableName);
                    } else {
                        logger.info("未知数据变动类型：{}", eventType);
                    }
                }
                long consumeTime = System.currentTimeMillis() - startTime;
                logger.info(execute_format, entry.getHeader().getLogfileName(), String.valueOf(entry.getHeader().getLogfileOffset()),
                        entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                        eventType, String.valueOf(rowChage.getRowDatasCount()), String.valueOf(consumeTime));
            }
        }
    }
}
