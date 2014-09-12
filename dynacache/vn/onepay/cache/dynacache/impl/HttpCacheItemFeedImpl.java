package vn.onepay.cache.dynacache.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

import vn.onepay.cache.dynacache.DynaCacheItemFeed;
import vn.onepay.cache.dynacache.DynaCacheItemFeedException;

public class HttpCacheItemFeedImpl implements DynaCacheItemFeed {
	
	private static MultiThreadedHttpConnectionManager connectionManager;
	private static HttpClient client;
	static {

		if (connectionManager == null) {
			connectionManager = new MultiThreadedHttpConnectionManager();
			HttpConnectionManagerParams params = new HttpConnectionManagerParams();
			params.setDefaultMaxConnectionsPerHost(100);
			params.setMaxTotalConnections(5000);
			params.setParameter(HttpConnectionManagerParams.SO_TIMEOUT, 10000);
			params.setParameter(HttpConnectionManagerParams.CONNECTION_TIMEOUT,
					10000);
			connectionManager.setParams(params);
		}
		if (client == null)
			client = new HttpClient(connectionManager);
	}
	// Key la Url
	@Override
	public Object feedItem(Object key) throws DynaCacheItemFeedException {
		return feed(key);
	}
	@Override
	public Object feed(Object key) throws DynaCacheItemFeedException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		String url=(String) key;
		System.out.println("URL: "+ url);
		int pos = url.indexOf('#');
		if (pos != -1) {
			return feedCachedItemByPostMethod(url);
		}else
			return feedCachedItemByGetMethod(url);
	}
	private String feedCachedItemByGetMethod(String url) throws DynaCacheItemFeedException {
		// TODO Auto-generated method stub
		client.getParams().setParameter("http.protocol.content-charset", "UTF-8");
		GetMethod method = new GetMethod(url);
		try {
			method.setRequestHeader("Content-Type",
					"application/x-www-form-urlencoded; charset=UTF-8");
			int response = client.executeMethod(method);
			if(response == HttpStatus.SC_OK){
				InputStream inputStream = null;
				try {
					inputStream = method.getResponseBodyAsStream();
					// parse stream to String
					Writer writer = new StringWriter();
					char[] buffer = new char[1024];
					Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
					int n;
					while ((n = reader.read(buffer)) != -1) {
						writer.write(buffer, 0, n);
					}
					String strContent = writer.toString();
					return strContent;
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	private String feedCachedItemByPostMethod(String url) throws DynaCacheItemFeedException {
		client.getParams().setParameter("http.protocol.content-charset", "UTF-8");
		String postData = null;
		int pos = url.indexOf('#');
		if (pos != -1) {
			postData = url.substring(pos + 1);
			url = url.substring(0, pos);
		}
		// TODO Auto-generated method stub
		PostMethod method=new PostMethod(url);
		try {
			method.setRequestHeader("Content-Type",
					"application/x-www-form-urlencoded; charset=UTF-8");
			method.setRequestBody(postData);
			int returnCode = client.executeMethod(method);
			if (returnCode == HttpStatus.SC_NOT_IMPLEMENTED) {
				System.err.println("The Post method is not implemented by this URI");
				// still consume the response body
			} else {
				InputStream inputStream = null;
				try {
					inputStream = method.getResponseBodyAsStream();
					// parse stream to String
					Writer writer = new StringWriter();
					char[] buffer = new char[1024];
					Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
					int n;
					while ((n = reader.read(buffer)) != -1) {
						writer.write(buffer, 0, n);
					}
					String strContent = writer.toString();
					return strContent;
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		return null;
	}
	
}
