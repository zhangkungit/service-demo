package com.yryz.common.zookeeper;

import com.yryz.common.context.Context;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2017/12/1
 * @description
 */
public class BaseIDGenerator implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(BaseIDGenerator.class);
    public static final String ID_NODE_NAMESPACE = "distributed_id_node";
    public static final String LOCK_ZNODE = "/lock";
    public static final long LOCK_ACQUIRE_EXPIRED_TIME = 1000L;

    protected static Integer init = null;

    private static CuratorFrameworkFactory.Builder builder;

    @Override
    public void afterPropertiesSet() throws Exception {
        initBuilder();
        SeqTask task = new SeqTask(Thread.currentThread().getName());
        task.run();
    }

    void initBuilder() {
        builder = CuratorFrameworkFactory.builder()
                .connectionTimeoutMs(1000)
                .connectString("192.168.30.36:2181")
                .defaultData("0".getBytes())
                .namespace(ID_NODE_NAMESPACE)
                .retryPolicy(new ExponentialBackoffRetry(3000, 3))
                .maxCloseWaitMs(5000)
                .threadFactory(new ThreadFactory() {
                    public final AtomicInteger counter = new AtomicInteger(0);

                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r, "curator-" + counter.getAndIncrement());
                        thread.setDaemon(true);
                        return thread;
                    }
                });


    }

    //利用Zookeeper实现分布式seq生成
    public static class SeqTask implements Runnable {
        private final String seqTaskName;
        public SeqTask(String seqTaskName) {
            this.seqTaskName = seqTaskName;
        }

        @Override
        public void run() {
            logger.info("start obtain seq from zookeeper");
            CuratorFramework client = builder.build();
            client.start();
            InterProcessMutex lock = new InterProcessMutex(client, LOCK_ZNODE);
            try {
                boolean retry = true;
                do {
                    if (lock.acquire(LOCK_ACQUIRE_EXPIRED_TIME, TimeUnit.MILLISECONDS)) {
                        Stat stat = new Stat();
                        byte[] oldData = client.getData().storingStatIn(stat).forPath(LOCK_ZNODE);
                        String s = new String(oldData);
                        AtomicInteger d = new AtomicInteger(NumberUtils.toInt(s, 1));
                        d.getAndIncrement();
                        client.setData().forPath(LOCK_ZNODE, String.valueOf(d).getBytes());
                        init = d.get();
                        retry = false;
                        logger.info("seqTaskName: {}, obtain seq result: {}", seqTaskName, d);
                    }
                } while (retry);
            } catch (Exception e) {
                logger.error("obtain seq result error", e);
            } finally {
                try {
                    if (lock.isAcquiredInThisProcess()) {
                        lock.release();
                    }
                } catch (Exception e) {
                    logger.error("finally release error", e);
                } finally {
                    CloseableUtils.closeQuietly(client);
                }
            }
        }
    }

}
