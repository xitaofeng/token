package com.lang.token.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liu_yeye
 * @date 2018-05-11 10:50
 */
public class StringUtils {
    private StringUtils(){}
    private static final Logger LOG = LoggerFactory.getLogger(StringUtils.class);
    public static boolean isEmpty(String value) {
        return isEmpty((CharSequence)value);
    }

    public static boolean isEmpty(CharSequence value) {
        return value == null || value.length() == 0;
    }
}
