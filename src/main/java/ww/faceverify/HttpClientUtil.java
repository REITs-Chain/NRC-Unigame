package ww.faceverify;
/**
 * Created by wangjiang on 16/10/18.
 */

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

public class HttpClientUtil {

    public String doPost(String url, StringEntity entity, String charset){
        Map<String, Object> hash = new HashMap<String, Object>();

        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try{
            httpClient = new SSLClient();
            httpPost = new HttpPost(url);
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);
            System.out.println("返回的状态码为:" + Integer.toString(response.getStatusLine().getStatusCode()));

            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,charset);
                }
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

}