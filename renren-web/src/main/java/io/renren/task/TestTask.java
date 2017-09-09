package io.renren.task;

import io.renren.util.Sendsms;

import java.util.Map;

import org.apache.commons.httpclient.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

/**
 * 测试定时任务(演示Demo，可删除)
 * 
 * testTask为spring bean的名称
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月30日 下午1:34:24
 */
@Component("testTask")
public class TestTask {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	
	public synchronized void test(String params){
		logger.info("我是带参数的test方法，正在被执行，参数为：" + params);
		System.out.println("执行之前"+Thread.currentThread().getId());
		try {
			Thread.sleep(10000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
//		SysUserEntity user = sysUserService.queryObject(1L);
//		System.out.println(ToStringBuilder.reflectionToString(user));
		System.out.println("执行之后"+Thread.currentThread().getId());
	}
	
	
	@SuppressWarnings("unchecked")
	public void test2(){
		logger.info("我是不带参数的test2方法，正在被执行");
		NameValuePair[] data = {
				new NameValuePair("account", "cf_xihuanqian"),
				new NameValuePair("password", "single1899"), 
				new NameValuePair("mobile", "15504400427"),
			    new NameValuePair("content", "尊敬的会员，您好，夏季新品已上市，请关注。退订回TD【互亿无线】"),
			    new NameValuePair("format", "json")
			};
			String url = "http://api.yx.ihuyi.com/webservice/sms.php?method=Submit";
			System.out.println((Map<String,String>)JSON.parse(Sendsms.send(data,url)));
		
		
	}
}
