package com.yryz.common.guava;

import com.google.common.base.Splitter;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2017/9/7
 * @description
 */
public class GuavaUtils {

    public static final Splitter DOT_SPLITTER = Splitter.on(",").omitEmptyStrings();

    public static final Splitter WAVY_LINE_SPLITTER = Splitter.on("~").omitEmptyStrings();

    public static final Splitter.MapSplitter MAP_SPLITTER = Splitter.on(";").omitEmptyStrings().withKeyValueSeparator(":");
}


