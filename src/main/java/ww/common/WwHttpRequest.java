package ww.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.NoRouteToHostException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

public class WwHttpRequest {
	
	public static String message="";
	
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
        	message=e.getMessage();
            return null;
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            param=URLDecoder.decode(param,"UTF-8");
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
        	message=e.getMessage();
            return null;
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
    
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param params
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public String get(String url_str,String params){
	  HttpURLConnection httpConn=null;
	  BufferedReader in=null;
	  String result="";
	  try {
		  String url_full=url_str;
		  if(!params.isEmpty()){
			  params=URLDecoder.decode(params,"UTF-8");
			  url_full+="?"+params;
		  }
		   URL url=new URL(url_full);
		   httpConn=(HttpURLConnection)url.openConnection();
		
		   //读取响应
		   if(httpConn.getResponseCode()==HttpURLConnection.HTTP_OK){
			    StringBuffer content=new StringBuffer();
			    String tempStr="";
			    in=new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
			    while((tempStr=in.readLine())!=null){
			    	content.append(tempStr);
			    }
			    in.close();
			    result= content.toString();
		   }else{
			   result="请求出现了问题!";
		   }		   
	  } catch (IOException e) {
		  message=e.getMessage();
          return null;
	  }finally{
		  if (httpConn != null)
			  httpConn.disconnect();
	  }
	  return result;
	}
    
    /**
     * 测试URL链接
     * @param url_str
     * @param params
     * @return 0-成功  1-无法链接到服务器  2-服务器回复HTTPStatus不等于200  3-URL格式有问题  4-io异常
     */
    public int test(String url_str,String params){
  	  HttpURLConnection httpConn=null;
  	  try {
  		  String url_full=url_str;
  		  if(!params.isEmpty()){
  			  url_full+="?"+params;
  		  }
  		   URL url=new URL(url_full);
  		   httpConn=(HttpURLConnection)url.openConnection();
  		
  		   //读取响应
  		   if(httpConn.getResponseCode()==HttpURLConnection.HTTP_OK){
  			   	java.io.InputStream in = httpConn.getInputStream();
  		   }else{
  			   return 2; //服务器回复HTTPStatus不等于200
  		   }		   
  	  } catch (NoRouteToHostException e) {
  		  return 1;//无法链接服务器
  	  } catch (MalformedURLException e) {
  		  return 3;//url格式有问题
	  } catch (IOException e) {
		  return 4;//io问题
	  }finally{
  		  if (httpConn != null)
  			  httpConn.disconnect();
  	  }
  	  return 0;//链接成功
  	}
    

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param params
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public String post(String url_str,String params) {
		URL url = null;
		HttpURLConnection httpurlconnection = null;
		StringBuilder result = new StringBuilder();
		try {
			url = new URL(url_str);
			// 以post方式请求
			httpurlconnection = (HttpURLConnection) url.openConnection();
			httpurlconnection.setDoOutput(true);
			httpurlconnection.setRequestMethod("POST");
			if(null!=params&&params.length()>0){
				params=URLDecoder.decode(params,"UTF-8");
				httpurlconnection.getOutputStream().write(params.getBytes("UTF-8"));
				httpurlconnection.getOutputStream().flush();
				httpurlconnection.getOutputStream().close();
			}
			// 获取页面内容
			java.io.InputStream in = httpurlconnection.getInputStream();
			java.io.BufferedReader breader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String str = breader.readLine();
			while (str != null) {
				result.append(str);
				str = breader.readLine();
			}
			breader.close();
			in.close();
		} catch (Exception e) {
			message=e.getMessage();
            return null;
		} finally {
			if (httpurlconnection != null)
				httpurlconnection.disconnect();
		}
		return result.toString().trim();
	}

    
}
