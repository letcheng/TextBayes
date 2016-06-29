package com.ruyuapp.counter;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Counter的工厂类
 * @author <a href="mailto:letcheng@ruyuapp.com">letcheng</a>
 * @version create at 2016年3月29日 20:04
 */
public class CounterFactory {

    public static Counter getInstance(CounterType counterType){
        Counter counter = null;
        Properties properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemResourceAsStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        switch (counterType){
            case FILE:
                counter = new FileCounter(new File(ClassLoader.getSystemResource(properties.getProperty("trainCorpusPathRoot","Sogou")).getPath()));
                break;
            case MYSQL:
                counter = new MySQLCounter();
                break;
        }
        return counter;
    }

    public static Counter getMySQLTypeInstance(int fromIndex1,int toIndex1,int fromIndex2,int toIndex2){
        return new MySQLCounter(fromIndex1,toIndex1,fromIndex2,toIndex2);
    }


    public static Counter getFileTypeInstance(int fromIndex1,int toIndex1,int fromIndex2,int toIndex2){
        Properties properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemResourceAsStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return new FileCounter(new File(ClassLoader.getSystemResource(properties.getProperty("trainCorpusPathRoot","Sogou")).getPath()),fromIndex1,toIndex1,fromIndex2,toIndex2);
    }
}
