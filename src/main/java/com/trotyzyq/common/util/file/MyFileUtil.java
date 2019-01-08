package com.trotyzyq.common.util.file;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.trotyzyq.common.util.HttpsClient;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author zyq on 2018/11/1.
 * 操作oss文件的工具类
 */
public class MyFileUtil {

    /**
     * 上传文件
     * @param file
     * @return 成功：全路径
     * @throws IOException
     */
    public static String uploadFile(MultipartFile file)  {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(FileConstant.UPLOADFILE_PATH_URL);
            InputStreamEntity inputEntry = new InputStreamEntity(file.getInputStream());
            httpPost.setEntity(inputEntry);
            CloseableHttpResponse response = httpclient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            /** 上传结果解析**/
            String uploadResult = EntityUtils.toString(httpEntity,"utf-8");
            response.close();
            httpclient.close();

            JSONObject jsonObject = JSON.parseObject(uploadResult);
            int code =(int)jsonObject.get("code");
            if (code == 1){
                return jsonObject.getString("pathName");
            }
            return null;
        }catch (Exception e){
            return null;
        }

    }

    /**
     * 删除文件
     * @param path
     */
    public static void deleteFile(String path){
        HttpsClient.get(FileConstant.DELETEFILE_PATH_URL + path,"utf-8");
    }

}
