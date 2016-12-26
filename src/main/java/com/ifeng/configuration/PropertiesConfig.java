package com.ifeng.configuration;

import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by zhanglr on 2016/3/29.
 */
public abstract class PropertiesConfig implements IPropertiesConfig{
    private String path;
    Properties pro = null;
    private static final Logger logger = Logger.getLogger(PropertiesConfig.class);
    public PropertiesConfig(String path){
        this.path = path;
        this.initFile();
    }
    @Override
    public Properties getProperties() {
        return pro;
    }

    @Override
    public void initFile() {
        try {
            pro = new Properties();
            InputStream inputStream =PropertiesConfig.class.getClassLoader().getResourceAsStream(path); // new BufferedInputStream(fileInputStream);
            pro.load(inputStream);
        }catch (Exception err){
            err.printStackTrace();
        }
    }
}
