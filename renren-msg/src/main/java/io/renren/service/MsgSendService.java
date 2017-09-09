package io.renren.service;

import io.renren.entity.MsgChannelEntity;
import io.renren.entity.MsgEntity;
/**
 * 短信发送服务器
 * @author Administrator
 *
 */
public interface MsgSendService {
	/**
	 * 短信单发
	 * @param 短信实体
	 * @param 短信通道
	 * @return 返回短信实体
	 */
	public MsgEntity singleSend(MsgEntity msg);
	
	/**
	 * 短信余额查询
	 * @param channel
	 * @return
	 */
	public int getBalance(MsgChannelEntity channel);
}
