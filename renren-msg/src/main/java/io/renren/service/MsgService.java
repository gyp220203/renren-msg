package io.renren.service;

import java.util.List;
import java.util.Map;

import io.renren.entity.MsgEntity;

public interface MsgService {
	List<MsgEntity> queryList(Map<String, Object> map);
	/**
	 * 按id查找
	 * @param msgId
	 * @return
	 */
	MsgEntity queryById(Long msgId);
	void save(MsgEntity msg);
	void update(MsgEntity msg);
	void deleteBatch(Long[] msgIdList);
	int queryByStatus(Map<String, Object> map);
	int queryTotal(Map<String, Object> map);
	
}
