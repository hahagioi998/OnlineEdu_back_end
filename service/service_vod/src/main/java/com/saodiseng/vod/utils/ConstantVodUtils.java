package com.saodiseng.vod.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantVodUtils implements InitializingBean {

    @Value("${aliyun.vod.file.keyid}")
    private String keyid;
    @Value("${aliyun.vod.file.keysecret}")
    private String keysecret;

    public static String KEYID;
    public static String KEYSECRET;
    @Override
    public void afterPropertiesSet() throws Exception {
        KEYID = keyid;
        KEYSECRET = keysecret;
    }
}
