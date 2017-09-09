package io.renren.service;

import java.util.List;
import java.util.Map;

import io.renren.entity.MsgBatchInfoEntity;

public interface MsgBatchInfoService {

	void updateSendNumber();

	void save(MsgBatchInfoEntity batchInfo);

	void update(MsgBatchInfoEntity batchInfo);

	List<MsgBatchInfoEntity> queryList(Map<String, Object> map);

	MsgBatchInfoEntity queryObject(String batchInfo_id);

	int queryTotal(Map<String, Object> map);

	void deleteBatch(String[] batchInfo_idList);

}