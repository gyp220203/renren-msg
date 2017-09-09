package io.renren.service;

import java.util.List;

import io.renren.entity.MsgEntity;
/**
 * 短信校验和持久化服务
 * @author gyp 2017/06/223
 *
 */
public interface MsgPrepareService {
	/**
	 * 批量持久化
	 * @param msgList
	 */
	int batchSaveMsg(List<MsgEntity> msgList);
	/**
	 * 单条持久化
	 * @param msg
	 * @return
	 */
	MsgEntity saveMsg(MsgEntity msg);
	
	/**
	 * 清空缓存
	 */
	void clearMap();
	

}