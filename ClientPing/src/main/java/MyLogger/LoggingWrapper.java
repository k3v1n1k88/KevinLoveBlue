/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyLogger;

import org.slf4j.LoggerFactory;

/**
 *
 * @author root
 */
public class LoggingWrapper {
    public static void debug(Class clazz,String msg,Exception ex){
        LoggerFactory.getLogger(clazz).debug(msg,ex);
    }
    public static void debug(Class clazz,String msg){
        LoggerFactory.getLogger(clazz).debug(msg);
    }
    public static void warn(Class clazz,String msg,Exception ex){
        LoggerFactory.getLogger(clazz).warn(msg,ex);
    }
    public static void warn(Class clazz,String msg){
        LoggerFactory.getLogger(clazz).warn(msg);
    }
    public static void error(Class clazz,String msg,Exception ex){
        LoggerFactory.getLogger(clazz).error(msg,ex);
    }
    public static void error(Class clazz,String msg){
        LoggerFactory.getLogger(clazz).error(msg);
    }
    public static void info(Class clazz,String msg,Exception ex){
        LoggerFactory.getLogger(clazz).info(msg,ex);
    }
    public static void info(Class clazz,String msg){
        LoggerFactory.getLogger(clazz).info(msg);
    }
    
}
