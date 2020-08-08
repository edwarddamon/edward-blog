package com.lhamster.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @author Damon_Edward
 * @version 1.0
 * @date 2020/7/20
 */
@Slf4j
public class LoginUtil {
    public static final String QQ_appId = "xxxxxxxxx";

    /**
     * 获取用户信息
     *
     * @param url
     * @return
     */
    public static JSONObject getUserInfo(String url) {
        JSONObject jsonObject = null;
        CloseableHttpClient client = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = null;
        HttpEntity entity = null;
        try {
            response = client.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!StringUtils.isEmpty(response)) {
            entity = response.getEntity();
        }
        if (entity != null) {
            String result = null;
            try {
                result = EntityUtils.toString(entity, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            jsonObject = JSONObject.parseObject(result);
        }
        httpGet.releaseConnection();
        return jsonObject;
    }
}
