package com.trotyzyq.common.util;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 *	http请求工具类
 *  @author wmc
 *  @CreatTime 2017年9月4日 上午10:48:01
 *  @since version 1.0.0
 */
@SuppressWarnings("deprecation")
public class HttpUtil {
	private static final Logger log = Logger.getLogger(HttpUtil.class);
	
	private static PoolingHttpClientConnectionManager connMgr;
	private static RequestConfig requestConfig;
	private static final int MAX_TIMEOUT = 7000;

	static {
		// 设置连接池
		connMgr = new PoolingHttpClientConnectionManager();
		// 设置连接池大小
		connMgr.setMaxTotal(100);
		connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());
		RequestConfig.Builder configBuilder = RequestConfig.custom();
		// 设置连接超时
		configBuilder.setConnectTimeout(MAX_TIMEOUT);
		// 设置读取超时
		configBuilder.setSocketTimeout(MAX_TIMEOUT);
		// 设置从连接池获取连接实例的超时
		configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
		// 在提交请求之前 测试连接是否可用
		configBuilder.setStaleConnectionCheckEnabled(true);
		requestConfig = configBuilder.build();
	}

	/**
	 * 发送 GET 请求（HTTP），不带输入数据
	 * 
	 * @param url
	 * @return
	 */
	public static String doGet(String url) {
		return doGet(url, new HashMap<String, Object>());
	}

	/**
	 * 发送 GET 请求（HTTP），K-V形式
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	@SuppressWarnings("resource")
	public static String doGet(String url, Map<String, Object> params) {
		String result = null;
		try {
		String apiUrl = url;
		StringBuffer param = new StringBuffer();
		int i = 0;
		for (String key : params.keySet()) {
			if (i == 0)
				param.append("?");
			else
				param.append("&");
			param.append(key).append("=").append(params.get(key));
			i++;
		}
		apiUrl += param;
	
		HttpClient httpclient = new DefaultHttpClient();
		
			HttpGet httpPost = new HttpGet(apiUrl);
			HttpResponse response = httpclient.execute(httpPost);
			response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				result = IOUtils.toString(instream, "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 发送 POST 请求（HTTP），不带输入数据
	 * 
	 * @param apiUrl
	 * @return
	 */
	public static String doPost(String apiUrl) {
		return doPost(apiUrl, new HashMap<String, Object>());
	}

	/**
	 * 发送 POST 请求（HTTP），K-V形式
	 * 
	 * @param apiUrl
	 *            API接口URL
	 * @param params
	 *            参数map
	 * @return
	 */
	public static String doPost(String apiUrl, Map<String, Object> params) {
		CloseableHttpResponse response = null;
		String httpStr = null;
		try {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(apiUrl);
	
	
			httpPost.setConfig(requestConfig);
			List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
				pairList.add(pair);
			}
			httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
			//发送请求
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			httpStr = EntityUtils.toString(entity, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return httpStr;
	}

	/**
	 * 发送 POST 请求（HTTP），JSON形式
	 * 
	 * @param apiUrl
	 * @param json
	 *            json对象
	 * @return
	 */
	public static String doPost(String apiUrl, Object json) {
		String httpStr = null;
		CloseableHttpResponse response = null;
		String rtnMsg = null;
		try {
		CloseableHttpClient httpClient = HttpClients.createDefault();
	
		HttpPost httpPost = new HttpPost(apiUrl);
		// 设置请求和传输超时时间3分钟
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(1000*60*3)
				.setConnectTimeout(1000*60*3).build();
		httpPost.setConfig(requestConfig);
		
		
			httpPost.setConfig(requestConfig);
			StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");
			httpPost.setEntity(stringEntity);
			//发送请求
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			httpStr = EntityUtils.toString(entity, "UTF-8");
		} catch (Exception e) {
//			if (e instanceof SocketTimeoutException || e instanceof ConnectTimeoutException) {
//				rtnMsg = "{\"code\":\"400\",\"msg\":\"超时\"}";
//				System.out.println(rtnMsg);
//			}
			rtnMsg = "{\"code\":\"400\",\"msg\":\"请求异常\"}";
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (rtnMsg != null)
				httpStr = rtnMsg;
		}
		return httpStr;
	}

	/**
	 * 发送 SSL POST 请求（HTTPS），K-V形式
	 * 
	 * @param apiUrl API接口URL
	 * @param params 参数map
	 * @return
	 */
	public static String doPostSSL(String apiUrl, Map<String, Object> params) {
		CloseableHttpResponse response = null;
		String httpStr = null;
		try {
			// 设置请求和传输超时时间3分钟
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(1000*60*3)
					.setConnectTimeout(1000*60*3).build();
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
				.setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
		HttpPost httpPost = new HttpPost(apiUrl);
	
		
			httpPost.setConfig(requestConfig);
			List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
				pairList.add(pair);
			}
			httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("utf-8")));
			//发送请求
			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			httpStr = EntityUtils.toString(entity, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return httpStr;
	}

	
	/**
	 * 发送 SSL POST 请求（HTTPS），json形式
	 * 
	 * @param apiUrl API接口URL
	 * @return
	 */
	public static String doPostSSL(String apiUrl, Object json) {
		CloseableHttpResponse response = null;
		String httpStr = null;
		try {
			// 设置请求和传输超时时间3分钟
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(1000*60*3)
					.setConnectTimeout(1000*60*3).build();
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
				.setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
		HttpPost httpPost = new HttpPost(apiUrl);
		
			httpPost.setConfig(requestConfig);
			StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");
			httpPost.setEntity(stringEntity);
			//发送请求
			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			httpStr = EntityUtils.toString(entity, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return httpStr;
	}
	
	
	/**
	 * 请求失败自动重试机制
	 * @description
	 * @param client
	 * @param request 
	 * @param count 重试次数
	 * @author wmc
	 * @return void
	 * @throws Exception 
	 * @since  1.0.0
	 */
	public static CloseableHttpResponse sendRequest(CloseableHttpClient client, HttpPost request, int count) throws Exception {
		CloseableHttpResponse response = null;
		for (int i = 0; i < count; i++) {
			try {
				response = client.execute(request);
				break;
			} catch (Exception e) {
				if(i==count-1){
					throw new Exception();
				}					
			}
		}
		return response;
	}

	/**
	 * 创建SSL安全连接
	 * 
	 * @return
	 */
	private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
		SSLConnectionSocketFactory sslsf = null;
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {

				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}

				@Override
				public void verify(String host, SSLSocket ssl) throws IOException {
				}

				@Override
				public void verify(String host, X509Certificate cert) throws SSLException {
				}

				@Override
				public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
				}
			});
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		return sslsf;
	}

	/**
	 * 测试方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		String s="https://wallet.heymore.com/heywallet/getErc20CoinList";
		Map<String,Object> map=new HashMap();
		String s1=doPostSSL(s,map);
		System.out.println(s1);
	}
	
	
	/**
	 * 
	 * 带文件的post
	 * @param url
	 * @param params 普通参数
	 * @param files 文件参数(key-附件名称 v-附件路径)
	 * @author wmc
	 * @return void
	 * @since  1.0.0
	 */
	public static String doPostFile(String url,Map<String, String> params, Map<String, String> files) throws Exception{  
		
		CloseableHttpClient httpClient = HttpClients.createDefault(); 
		CloseableHttpResponse response = null;
		String httpStr = null;
		String rtnMsg = null;
		
        try {  
            HttpPost httppost = new HttpPost(url);  
            ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8);
            
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();  
            builder.setCharset(Charset.forName(HTTP.UTF_8));//设置编码
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//设置浏览器兼容模式  
            //遍历map上传文件
            for (Map.Entry<String, String> entry : files.entrySet()) {
            	 FileBody bin = new FileBody(new File(entry.getValue()));  
            	 builder.addPart(entry.getKey(), bin);     
          	 }
            //遍历添加参数
            for (Map.Entry<String, String> entry : params.entrySet()) {
            	builder.addPart(entry.getKey(),  new StringBody(entry.getValue(),contentType));  
            	 }
            log.info("http请求开始，接口："+url);
            
            HttpEntity reqEntity = builder.build();
            httppost.setEntity(reqEntity);  
            
            response = httpClient.execute(httppost);     //发送请求
            int statusCode = response.getStatusLine().getStatusCode();  
            if(statusCode == HttpStatus.SC_OK){
                System.out.println("服务器正常响应.....");  
                
                HttpEntity entity = response.getEntity();
                httpStr = EntityUtils.toString(entity, "utf-8");
             log.info("http请求响应，结果："+httpStr);    
            }  
            
            } catch (Exception e) {  
            	rtnMsg = "{\"code\":\"400\",\"msg\":\"请求异常\"}";
            	log.error(e.getMessage(),e);
            } finally {  
        			if (response != null) {
        				try {
        					EntityUtils.consume(response.getEntity());
        				} catch (IOException e) {
        					e.printStackTrace();
        				}
        			}
        			if (rtnMsg != null)
        				httpStr = rtnMsg;
        		}
        	return httpStr;
        }


}
