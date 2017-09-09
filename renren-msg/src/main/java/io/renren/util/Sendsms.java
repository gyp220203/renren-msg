package io.renren.util;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;

public class Sendsms {
	public static String send(NameValuePair[] sendData,String url){
		HttpClient client = new HttpClient(new HttpClientParams(),new SimpleHttpConnectionManager(true) );
		PostMethod method = new PostMethod(url);
		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");
		method.setRequestBody(sendData);
		try {
			client.executeMethod(method);
			return method.getResponseBodyAsString();
		
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			method.releaseConnection(); 
		}
		return null; 
		
	}
	public static int getCost(int max,String msg){
		return (int) Math.ceil(msg.length()/(double)max);
	}
}
