package io.renren.service;


import io.renren.entity.MsgChannelEntity;

import java.util.List;
import java.util.Map;

public interface MsgChannelService {
	/**
	 * 查询通道列表
	 * @param map
	 * @return
	 */
	List<Long> queryChannelUserList(Long channelId);
	
	
	List<MsgChannelEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String,Object> map);
	
	MsgChannelEntity   queryById(Long id);
	
	void save(MsgChannelEntity channel);
	
	void update(MsgChannelEntity channel);
	
	void delete(Long channelId);
	
	void deleteBatch(Long[] channelIdList);
	
	void updateBalance();
}
