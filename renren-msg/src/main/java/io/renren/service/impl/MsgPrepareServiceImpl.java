package io.renren.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.renren.entity.MsgChannelEntity;
import io.renren.entity.MsgEntity;
import io.renren.entity.MsgTemplateEntity;
import io.renren.service.MsgChannelService;
import io.renren.service.MsgPrepareService;
import io.renren.service.MsgService;
import io.renren.service.MsgTemplateService;
import io.renren.util.MessageFormatUtil;
import io.renren.util.MsgConstant;
import io.renren.util.ShortUUID;
@Service("msgPrepareService")
public class MsgPrepareServiceImpl implements MsgPrepareService {
	
	@Autowired
	MsgService msgService;
	
	@Autowired
	MsgTemplateService templateService;
	
	@Autowired
	MsgChannelService channelService;
	
	private static Map<Long, MsgTemplateEntity> templateMap = new ConcurrentHashMap<Long, MsgTemplateEntity>();
	private static Map<Long, MsgChannelEntity> channelMap = new ConcurrentHashMap<Long, MsgChannelEntity>();
	
	@Override
	@Transactional
	public int batchSaveMsg(List<MsgEntity> msgList) {
		int checkNumber=0;
		if(msgList==null||msgList.size()==0){
			return checkNumber;
		}
		
		synchronized (this) {
			if(channelMap.size()==0||templateMap.size()==0){
				List<MsgChannelEntity> channelList =channelService.queryList(new HashMap<String, Object>());
				List<MsgTemplateEntity> templateList =templateService.queryList(new HashMap<String, Object>());
				for(MsgChannelEntity channel:channelList){
					channelMap.put(channel.getChannelId(), channel);
				}
				for(MsgTemplateEntity template:templateList){
					templateMap.put(template.getTemplateId(), template);
				}
			}
		}
		
		for(MsgEntity msg :msgList){
			if(msg==null){
				continue;
			}
			msg.setStyle(MsgConstant.MsgStyle.BATCH.getValue());
			saveMsg(msg);
			if(MsgConstant.MsgStatus.WAIT.getValue()==msg.getStatus()){
				checkNumber++;
			}
		}
		return checkNumber;
	}

	@Override
	public MsgEntity saveMsg(MsgEntity msg) {
		if(msg==null){
			return null;
		}
		msg.setMsgId(ShortUUID.generate());
		msg.setStatus(MsgConstant.MsgStatus.INIT.getValue());
		checkTelNum(msg);
		checkTemplate(msg);
		checkChannel(msg);
		checkUser(msg);
		if(MsgConstant.MsgStatus.ERROR.getValue()!=msg.getStatus()){
			msg.setStatus(MsgConstant.MsgStatus.WAIT.getValue());
		}
		msgService.save(msg);
		return msg;
	}
	/**
	 * 校验电话号码
	 * @param msg
	 * @return
	 */
	private MsgEntity checkTelNum(MsgEntity msg){
		if(StringUtils.isEmpty(msg.getTelNumber())){
			return setError(msg,"电话号码为空");
		}
        Pattern p = Pattern.compile("^[1][0-9]{10}$"); // 验证手机号  
        Matcher m = p.matcher(msg.getTelNumber());  
        if(!m.matches()){
        	return setError(msg,"电话号码格式不正确");
        }
		return msg;
		
	}
	/**
	 * 校验模版
	 * @param msg
	 * @return
	 */
	private MsgEntity checkTemplate(MsgEntity msg){
		if(MsgConstant.MsgStatus.ERROR.getValue()==msg.getStatus()){
			return msg;
		}
		MsgTemplateEntity template =null;
		if(msg.getTemplateId()==null){
			return setError(msg,"模版不能为空");
		}
		template=templateMap.get(msg.getTemplateId());
		if(template==null){
			template=templateService.queryById(msg.getTemplateId());
			if(template==null){
				return setError(msg,"未找到模版");
			}
			templateMap.put(template.getTemplateId(), template);
		}
		msg.setTemplateName(template.getTemplateName());
		if(template.getChannelId()==null){
			return setError(msg,"模版未绑定通道");
		}
		if(StringUtils.isEmpty(msg.getContent())){
			return setError(msg,"短信内容为空");
		}
		String[] contents = msg.getContent().split("\\|");
		if(template.getParameterNo()!=contents.length){
			return setError(msg,"短信内容与模版不符");
		}
		msg.setContent(buildContent(template.getTemplateBody(), contents));
		if(StringUtils.isNotEmpty(template.getSignature())){
			msg.setContent(msg.getContent()+"【"+template.getSignature()+"】");
		}
		msg.setChannelId(template.getChannelId());
		return msg;
	}
	/**
	 * 校验通道
	 * @param msg
	 * @return
	 */
	private MsgEntity checkChannel(MsgEntity msg){
		if(MsgConstant.MsgStatus.ERROR.getValue()==msg.getStatus()){
			return msg;
		}
		MsgChannelEntity channel=null;
		channel=channelMap.get(msg.getChannelId());
		if(channel==null){
			channel=channelService.queryById(msg.getChannelId());
			//模版中channelId为外键，缓存中没有，数据库中肯定有；
			channelMap.put(channel.getChannelId(), channel);
		}
	
		msg.setChannelName(channel.getChannelName());
		if(MsgConstant.MsgChannelStyle.OFF.getValue()==channel.getStatus()){
			return setError(msg,"模版所用通道已被禁用");
		}
		if(channel.getImplementId()==null){
			return setError(msg,"模版所用通道未绑定短信服务");
		}
		msg.setChannel(channel);
		return msg;
	}
	/**
	 * 校验用户
	 * @param msg
	 * @return
	 */
	private MsgEntity checkUser(MsgEntity msg){
		if(MsgConstant.MsgStatus.ERROR.getValue()==msg.getStatus()){
			return msg;
		}
		List<Long> userIdList = channelService.queryChannelUserList(msg.getChannelId());
		if(!userIdList.contains(msg.getUserId())){
			return setError(msg,"用户无权使用该通道和模版");
		}
		return msg;
	}
	
	
	/**
	 * 异常设定
	 * @param msg
	 * @param error消息
	 * @return
	 */
	private MsgEntity setError(MsgEntity msg,String error){
		msg.setStatus(MsgConstant.MsgStatus.ERROR.getValue());
		msg.setStatusMsg(error);
		return msg;
	}
	/**
	 * 拼接短信
	 * @param template
	 * @param contents
	 * @return
	 */
	private String  buildContent(String template,String[] contents){
		return MessageFormatUtil.format(template, contents);
	}

	@Override
	public void clearMap() {
		templateMap.clear();
		channelMap.clear();
	}
	
}
