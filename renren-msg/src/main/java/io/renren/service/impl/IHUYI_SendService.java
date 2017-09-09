package io.renren.service.impl;

import java.util.Date;
import java.util.Map;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import io.renren.entity.MsgChannelEntity;
import io.renren.entity.MsgEntity;
import io.renren.service.MsgSendService;
import io.renren.util.MsgConstant;
import io.renren.util.Sendsms;
import io.renren.utils.DateUtils;
import io.renren.utils.RRException;
@Service("ihuyi_SendService")
public class IHUYI_SendService implements MsgSendService {
	final static int RESENDCOUNT=3;
	
	@Override
	public MsgEntity singleSend(MsgEntity msg) {
		if(msg==null){
			return null;
		}
		//条件筛选
		if(MsgConstant.MsgStatus.WAIT.getValue()!=msg.getStatus()){
			return msg;
		}
		MsgChannelEntity channel = msg.getChannel();
		//初始化参数
		NameValuePair[] data = {
			new NameValuePair("account", channel.getUsername()),
			new NameValuePair("password", channel.getPassword()), 
			new NameValuePair("mobile", msg.getTelNumber()),
			new NameValuePair("content", msg.getContent()),
			new NameValuePair("stime",
					msg.getSendTime()==null?
							"":DateUtils.format(msg.getSendTime(),DateUtils.DATE_TIME_PATTERN)),
			new NameValuePair("format", "json")
		};
		String submitResult=null;
		int count=0;
		//三次尝试
		do{
			count++;
			submitResult= Sendsms.send(data, channel.getUrl4send());
		}while(StringUtils.isEmpty(submitResult)&&count<RESENDCOUNT);
		//确认服务器没有相应
		if(StringUtils.isEmpty(submitResult)){
			msg.setStatus(MsgConstant.MsgStatus.ERROR.getValue());
			msg.setStatusMsg("短信服务器无响应");
			return msg;
		}
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Map<String, Object> maps = (Map)JSON.parse(submitResult);
		//返回信息存储
		
		msg.setResponseCode(String.valueOf(maps.get("code")));
		msg.setResponseMsg(String.valueOf(maps.get("msg")));
		//返回code非2皆为异常
		if(!"2".equals(String.valueOf(maps.get("code")))){
			msg.setStatus(MsgConstant.MsgStatus.ERROR.getValue());
			msg.setStatusMsg("接口服务异常");
			return msg;
		}
		msg.setCost(Sendsms.getCost(65, msg.getContent()));
		if(msg.getSendTime()==null){
			msg.setSendTime(new Date());
		}
		msg.setStatus(MsgConstant.MsgStatus.COMPLETE.getValue());
		return msg;
	}
	

	@Override
	public int getBalance(MsgChannelEntity channel) {
		if(channel==null){
			return 0;
		}
		NameValuePair[] data = {
				new NameValuePair("account", channel.getUsername()),
				new NameValuePair("password", channel.getPassword()), 
				new NameValuePair("format", "json")
		};
		String submitResult=null;
		int count=0;
		//三次尝试
		do{
			count++;
			submitResult= Sendsms.send(data, channel.getUrl4query());
		}while(StringUtils.isEmpty(submitResult)&&count<RESENDCOUNT);
		//确认服务器没有相应
		if(StringUtils.isEmpty(submitResult)){
			throw new RRException("服务器没有响应");
		}
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Map<String, Object> maps = (Map)JSON.parse(submitResult);
		String code =String.valueOf(maps.get("code")).trim();
		String num =String.valueOf(maps.get("num")).trim();
		if(!"2".equals(code)){
			throw new RRException("channelId:"+channel.getChannelId()+"查询异常,code:"+maps.get("code")+"异常描述:"+maps.get("msg"));
		}
		if(StringUtils.isEmpty(num)||!StringUtils.isNumeric(num)){
			throw new RRException("channelId:"+channel.getChannelId()+"查询异常,换回num值为"+maps.get("num"));
		}
		return Integer.parseInt(num);
	}

	
	

}
