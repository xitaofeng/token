package com.lang.token.util;

/**
 * @author liu_yeye
 * @date 2018-05-11 15:06
 */

public interface LangTokenUtil {
     boolean push(String accessToken);
     boolean contains(String accessToken);
     boolean pop(String accessToken);
     int size();
}
