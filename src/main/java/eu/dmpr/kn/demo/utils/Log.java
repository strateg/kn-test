package eu.dmpr.kn.demo.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Log {

    public static void info(String format, Object... object){
        log.info(format, object);
    }

    public static void warn(String format, Object... object){
        log.warn(format, object);
    }

    public static void error(String format, Object... object){
        log.error(format, object);
    }

    public static void error(String format){
        log.error(format);
    }
}
