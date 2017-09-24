package com.mxcx.erp.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

public class HttpClientUtil {

    private static final int BYTE_SIZE = 10 * 1024;

    public static byte[] sendUrl(String path, byte[] data) {

        HttpURLConnection connection = null;
        BufferedReader br = null;
        try {

            URL url = null;
            url = new URL(path);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/octet-stream");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.connect();
            DataOutputStream dataOut = new DataOutputStream(connection.getOutputStream());
            dataOut.write(data);
            dataOut.flush();
            dataOut.close();
            // dataOut.close();
            String line = null;
            StringBuilder sb = new StringBuilder();
            if (connection.getResponseCode() == 200) {
                // br = new BufferedReader(new InputStreamReader(
                // connection.getInputStream()));
                // while ((line = br.readLine()) != null) {
                // sb.append(line);
                // }
                InputStream inStrm = connection.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(inStrm);

                // 数据字节数组
                byte[] receData = new byte[BYTE_SIZE];
                System.out.println(new String(receData));
                bis.read(receData);

                return receData;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    public static byte[] post(String url, byte[] data) {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        
        byte[] response = null;
        try {
            ByteArrayEntity byteArrayEntity = new ByteArrayEntity(data);
            post.setEntity(byteArrayEntity);

            Long startTime = System.currentTimeMillis();
            HttpResponse res = client.execute(post);
            System.out.println(System.currentTimeMillis() - startTime);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = res.getEntity();
                String charset = EntityUtils.getContentCharSet(entity);
                if (charset == null) {
                    charset = "utf-8";
                }

                BufferedInputStream bis = new BufferedInputStream(entity.getContent());
                // 数据字节数组
                byte[] receData = new byte[BYTE_SIZE];
                bis.read(receData);
                response = receData;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }
    
    public static String post(String url, Map<String, String> params) {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        String result = null;
        
        //创建表单参数列表
        List<NameValuePair> qparams = new ArrayList<NameValuePair>();
        Set<String> keys = params.keySet();
        for(String key : keys)
        {
            qparams.add(new BasicNameValuePair(key, params.get(key)));
        }
        
        try { 
            //填充表单
            post.setEntity(new UrlEncodedFormEntity(qparams, "UTF-8"));

            HttpResponse res = client.execute(post);

            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = res.getEntity();
                
                String charset = EntityUtils.getContentCharSet(entity);
                if (charset == null) {
                    charset = "utf-8";
                }
                
                if(entity != null)
                {
                    entity = new BufferedHttpEntity(entity);
                    
                    BufferedInputStream in = new BufferedInputStream(entity.getContent());
                    // 数据字节数组
                    byte[] read = new byte[BYTE_SIZE];
                    /*
                    byte[] all = new byte[0];
                    int num;
                    while((num = in.read()) > 0)
                    {
                        byte[] temp = new byte[all.length + num];
                        System.arraycopy(all, 0, temp, 0, all.length);
                        System.arraycopy(read, 0, temp, all.length, num);
                        all = temp;
                    }
                    result = new String(all, "UTF-8");
                    if(null != in)
                    {
                        in.close();
                    }
                    
                    post.abort();
                    */
                    
                    //*
                    in.read(read);
                    result = new String(read, "UTF-8");
                    //*/
                }
               
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    
    public static String get(String url) {
        HttpGet httpGet = new HttpGet(url);
        HttpClient httpClient = new DefaultHttpClient();
        String result = null;
        try {
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 16000); //连接超时
            HttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                String charset = EntityUtils.getContentCharSet(entity);
                if (charset == null) {
                    charset = "utf-8";
                }
                if(entity != null)
                {
                    entity = new BufferedHttpEntity(entity);
                    
                    BufferedInputStream in = new BufferedInputStream(entity.getContent());
                    // 数据字节数组
                    byte[] read = new byte[BYTE_SIZE];
                    in.read(read);
                    result = new String(read, "UTF-8");
                    //*/
                }
            }
            return result;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public static String getString(String url) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse response = httpClient.execute(httpGet);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity httpEntity = response.getEntity();
				String charset = EntityUtils.getContentCharSet(httpEntity);
				if(charset == null){
					charset = "utf-8";
				}
				return dump(httpEntity, charset);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return "";
	}
//    public static String post1(String url,JSONObject json){
//        HttpClient client = new DefaultHttpClient();
//        HttpPost post = new HttpPost(url);
//        String result = null;
//        try {
//            StringEntity s = new StringEntity(json.toString());
//            s.setContentEncoding("UTF-8");
//            s.setContentType("application/json");
//            post.setEntity(s);
//            
//            HttpResponse res = client.execute(post);
//            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
//                HttpEntity entity = res.getEntity();
//                String charset = EntityUtils.getContentCharSet(entity);
//                if (charset == null) {
//                    charset = "utf-8";
//                }
//                
//                if(entity != null)
//                {
//                    entity = new BufferedHttpEntity(entity);
//                    System.out.println("entity.getContent():"+entity.getContent());
//                    BufferedInputStream in = new BufferedInputStream(entity.getContent());
//                    // 数据字节数组
//                    byte[] read = new byte[BYTE_SIZE];
//                    in.read(read);
//                    result = new String(read, "UTF-8");
//            }
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        return result;
//    }
    
    public static String post1(String url,JSONObject json){
        PrintWriter out = null;  
        BufferedReader in = null;  
        String result = "";  
        try {  
            URL realUrl = new URL(url);  
            // 打开和URL之间的连接  
            URLConnection conn = realUrl.openConnection();  
            //设置通用的请求属性  
            conn.setRequestProperty("user-agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:21.0) Gecko/20100101 Firefox/21.0)");  
            // 发送POST请求必须设置如下两行  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            // 获取URLConnection对象对应的输出流  
            OutputStreamWriter outWriter = new OutputStreamWriter(conn.getOutputStream(), "utf-8");  
            out = new PrintWriter(outWriter);  
            // 发送请求参数  
            out.print(json.toString());  
            // flush输出流的缓冲  
            out.flush();  
            // 定义BufferedReader输入流来读取URL的响应  
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
            String line;  
            while ((line = in.readLine()) != null) {  
                in = new BufferedReader(new InputStreamReader(conn.getInputStream()));result += line;  
            }  
        } catch (Exception e) {  
            System.out.println("发送 POST 请求出现异常！"+e);  
            e.printStackTrace();  
        }  
        //使用finally块来关闭输出流、输入流  
        finally{  
            try{  
                if(out!=null){  
                    out.close();  
                }  
                if(in!=null){  
                    in.close();  
                }  
            }  
            catch(IOException ex){  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    }
    
    public static boolean getResponseByPost(String url, Integer timeoutMillSec, Map<String, String> params) {
    	HttpClient httpClient = new DefaultHttpClient();
    	HttpPost httpPost = new HttpPost(url);
    	httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, timeoutMillSec);
		try {
			List<NameValuePair> qparams = new ArrayList<NameValuePair>();
	        Set<String> keys = params.keySet();
	        for(String key : keys)
	        {
	            qparams.add(new BasicNameValuePair(key, params.get(key)));
	        }
			httpPost.setEntity(new UrlEncodedFormEntity(qparams));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
	    		return true;
	    	}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
    }
    
    public static String dump(HttpEntity entity, String charset) throws IOException {

		String resulthtmlsource = "";
		InputStream input = entity.getContent();
		// Header contentType = entity.getContentType();

		try {
			// BufferedReader br = new BufferedReader(new InputStreamReader(input, "GBK"));
			// BufferedReader br = new BufferedReader(new InputStreamReader(input, "gb2312"));
			BufferedReader br = new BufferedReader(new InputStreamReader(input, charset));

			resulthtmlsource = IOUtils.toString(br);

			// String line;
			// while ((line = br.readLine()) != null) {
			// resulthtmlsource += line;
			// }

		} catch (Exception e) {
			//log.error(WebUtil.getStackTrace(e));
			e.printStackTrace();
			resulthtmlsource = "";
			return null;
		} finally {
			// 保证InputStream的关闭.
			IOUtils.closeQuietly(input);
		}
		System.out.println(resulthtmlsource);
		return resulthtmlsource;

	}
    public static void main(String[] args) {
		Map<String,Object> map=new HashMap<String,Object>();
		String s="暗红色//的哈塑=料,---科技";
		map.put("keji", s);
		Gson g=new Gson();
		String s1=g.toJson(map);
		System.out.println(s1);
//		JSONObject j=new JSONObject();
		System.out.println(JSONObject.fromObject(map));
	}
}
