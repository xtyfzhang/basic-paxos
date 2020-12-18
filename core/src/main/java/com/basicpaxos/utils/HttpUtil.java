package com.basicpaxos.utils;

import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;

public class HttpUtil {

    /**
     * get请求
     * @return
     */
    public static String get(String url,String parameter){
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();

        //设置请求超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(60000)
                .setConnectTimeout(60000)
                .setConnectionRequestTimeout(60000)
                .build();

        try {
            URI uri = new URIBuilder(url).setParameter("instanceInfo", parameter).build();
            HttpGet request = new HttpGet(uri);
            //给这个请求设置请求配置
            request.setConfig(requestConfig);
            //request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) ...");
            CloseableHttpResponse response = closeableHttpClient.execute(request);
            if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                String result = EntityUtils.toString(response.getEntity());// 返回json格式：
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {                //关闭流并释放资源
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }


    /**
     * post请求
     * @return
     */
    public static String post(String url,String parameter){
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();

        //设置请求超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(60000)
                .setConnectTimeout(60000)
                .setConnectionRequestTimeout(60000)
                .build();

        try {
            URI uri = new URIBuilder(url).setParameter("instanceInfo", parameter).build();
            HttpPost request = new HttpPost(uri);
            StringEntity stringEntity = new StringEntity(parameter);
            stringEntity.setContentType("application/json;charset=utf-8");
            request.setEntity(stringEntity);
            //给这个请求设置请求配置
            request.setConfig(requestConfig);
            //request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) ...");
            CloseableHttpResponse response = closeableHttpClient.execute(request);
            if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                return EntityUtils.toString(response.getEntity());// 返回json格式：
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {                //关闭流并释放资源
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
