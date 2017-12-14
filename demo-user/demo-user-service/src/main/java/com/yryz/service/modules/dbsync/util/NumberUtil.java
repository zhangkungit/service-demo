package com.yryz.service.modules.dbsync.util;

import java.math.BigDecimal;

/**
 * 介绍
 *
 * @author zhangkun
 * @version 1.0
 * @date 2017/12/5
 * @description
 */
public class NumberUtil {
    /**
     * 格式化数据为Long型
     *
     * @param number
     * @return
     */
    public static Long numberToLong(Object number) {
        if (number != null) {
            if (number instanceof Long) {
                return (Long) number;
            }
            if (number instanceof Double) {
                BigDecimal bd = new BigDecimal((Double) number);
                return bd.longValue();
            }
            if (number instanceof Integer) {
                return ((Integer) number).longValue();
            }
            if (number instanceof String) {
                BigDecimal bd = new BigDecimal((String) number);
                return bd.longValue();
            }
        }
        return 0L;
    }

    public static Integer numberToInteger(Object number) {
        if (number != null) {
            if (number instanceof Long) {
                return ((Long) number).intValue();
            }
            if (number instanceof Double) {
                BigDecimal bd = new BigDecimal((Double) number);
                return bd.intValue();
            }
            if (number instanceof Integer) {
                return (Integer) number;
            }
            if (number instanceof String) {
                BigDecimal bd = new BigDecimal((String) number);
                return bd.intValue();
            }
        }
        return 0;
    }
}
