package com.ifeng.utils;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpHelper {
	
	private static ExecutorService executorService = Executors.newFixedThreadPool(5);
	
	public static void shutdownThreadPoolNow() {
		executorService.shutdownNow();
	}
	
	public static void shutdownThreadPool() {
		executorService.shutdown();
	}
	public static void openNewExecutorService(int RunningThreadCount) {  
        if (!executorService.isShutdown())  
            executorService.shutdown();  
        executorService = Executors.newFixedThreadPool(RunningThreadCount);  
    }
	
	public interface AsynExecuteCallBack {  
        public void beforeExecute();  
  
        public void afterExecute(HttpReturnData rData);  
  
        public void exceptionOccored(Exception e);  
    }
	
	public static void AsynGetData(final String url,final HttpAttr attr,final String encoding,
			final AsynExecuteCallBack callback) {
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				callback.beforeExecute();
				try {
					HttpReturnData data = getDataBytes(url, attr, null, false, encoding);
					callback.afterExecute(data);
				} catch (Exception e) {
					e.printStackTrace();
					callback.exceptionOccored(e);
					// TODO: handle exception
				}
			}
		});
	}
	
	public static void AsynPostData(final String url,final HttpAttr attr, final String postData,final String encoding,
			final AsynExecuteCallBack callback) {
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				callback.beforeExecute();
				try {
					HttpReturnData data = getDataBytes(url, attr, postData, true, encoding);
					callback.afterExecute(data);
				} catch (Exception e) {
					e.printStackTrace();
					callback.exceptionOccored(e);
				}
			}
		});
	}
	
	public static String getData(final String url ,final HttpAttr attr,final String encoding) {
		try {
			HttpReturnData data = getDataBytes(url, attr, null, false, encoding);
			return data.getData();
		} catch (Exception e) {
			 e.printStackTrace();  
	         return null;
		}
	}
	
	public static String postData(final String url ,final HttpAttr attr,final String postData, final String encoding) {
		try {
			HttpReturnData data = getDataBytes(url, attr, postData, true, encoding);
			return data.getData();
		} catch (Exception e) {
			e.printStackTrace();  
            return null;
		}
		
	}
	
	public static HttpReturnData getDataBytes(String strUrl,
			HttpAttr attr,
			String param,
			boolean isPost,
			String encoding
			){
		
		
		HttpURLConnection.setFollowRedirects(true);
		HttpURLConnection connection = null;
		InputStream inputStream = null;
		BufferedInputStream bufferedInputStream = null;
		String result = "";
		
		try {
			URL url = new URL(strUrl);
			connection = (HttpURLConnection)url.openConnection();
			connection.setReadTimeout(attr.getTimeout());
			connection.setConnectTimeout(attr.getTimeout());
			connection.setUseCaches(attr.getIsUseCache());
			
			if (attr.getIsKeepAlive())  
				connection.setRequestProperty("connection", "Keep-Alive");  
			connection.setInstanceFollowRedirects(attr  
                    .getIsInstanceFollowRedirect());// 设置跳转跟随  
			connection.setRequestProperty("Referer", attr.getReferer());  
			connection.setRequestProperty("Content-Type",  
                    attr.getContentType());  
			connection.setRequestProperty("Accept", attr.getAccept());  
			connection.setRequestProperty("User-Agent",  
                    attr.getUserAgent());
			connection.setRequestProperty("Accept-Language",  
                    "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
			
			if (isPost){
				connection.setDoInput(true);
				connection.setDoOutput(true);
				connection.setUseCaches(false);
				connection.setRequestProperty("Context-Length",  
                        String.valueOf(param.getBytes(encoding).length));
				connection.setRequestMethod("POST");
				connection.connect();
				OutputStream oStream = null;
				try {
					oStream = connection.getOutputStream();
					oStream.write(param.getBytes(encoding));
					oStream.flush();
				} catch (Exception e) {
					if (oStream != null)
						oStream.close();
				}
			}
			inputStream = connection.getInputStream();
			bufferedInputStream = new BufferedInputStream(inputStream);
			byte[] 	buffer = new byte[1024 * 10];
			int count = 0;
			while ((count = bufferedInputStream.read(buffer)) != -1) {
				result +=(new String(buffer,0,count,encoding));
			}
		} catch (Exception e) {
			e.printStackTrace();
			result= "timeout";
		}
		finally{
			try {
				if (bufferedInputStream != null)
					bufferedInputStream.close();
				if (inputStream != null)
					inputStream.close();
				connection.disconnect(); 
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return new HttpReturnData(result, attr, encoding);
	}
}
