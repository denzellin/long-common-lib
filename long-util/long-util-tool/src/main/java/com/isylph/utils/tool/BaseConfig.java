package com.isylph.utils.tool;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

//配置文件:配置文件是config.properties放在classes目录下
public class BaseConfig {

    private final static String MAX_LIST_NUM = "system.list.max";

    private final static int MAX_LIST_CNT = 600;
    private  static String APPLICATION_PATH = "system";  //应用配置
    private  static String REDIS_PATH = "redis"; //http请求地址配置

    private static ResourceBundle bundle;
    private static ResourceBundle redisBundle;

    public static void init(String appPath, String RedisPath){
        APPLICATION_PATH = appPath;
        REDIS_PATH = RedisPath;
        loadConfig();
    }

    public static String getValueByKey(String key){
        return bundle.getString(key);
    }

    public static Long getLongValue(String Key){
        try {
            return Long.valueOf(bundle.getString(Key));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static int getMaxListCnt(){
        try {
            Integer cnt= Integer.valueOf(bundle.getString(MAX_LIST_NUM));
            if (null == cnt || cnt> MAX_LIST_CNT ){
                return MAX_LIST_CNT;
            }else{
                return cnt;
            }
        }catch (Exception e){
            e.printStackTrace();
            return MAX_LIST_CNT;
        }
    }

    public static String getRedisPropertiesByKey(String key){
        return redisBundle.getString(key);
    }

    public static void reloadConfig(){
        ResourceBundle.clearCache(); //清除原配置
        loadConfig();
    }

    /**
     * 加载配置
     */
    public static void loadConfig(){
        //重新加载应用配置
        bundle = ResourceBundle.getBundle(APPLICATION_PATH, new ResourceBundle.Control() {
            public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader,
                                            boolean reload) throws IllegalAccessException, InstantiationException, IOException {
                // 将reload标识位置为true
                return super.newBundle(baseName, locale, format, loader, true);
            }
        });
        //重新加载url配置
        redisBundle = ResourceBundle.getBundle(REDIS_PATH, new ResourceBundle.Control() {
            public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader,
                                            boolean reload) throws IllegalAccessException, InstantiationException, IOException {
                // 将reload标识位置为true
                return super.newBundle(baseName, locale, format, loader, true);
            }
        });
    }

}
