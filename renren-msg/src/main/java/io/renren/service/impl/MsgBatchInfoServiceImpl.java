package io.renren.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.renren.dao.MsgBatchInfoDao;
import io.renren.entity.MsgBatchInfoEntity;
import io.renren.service.MsgBatchInfoService;
import io.renren.util.MsgConstant;

/**
 *
 * @author gaoyupeng
 * @date 2017年6月28日
 */
@Service("msgBatchInfoService")
public class MsgBatchInfoServiceImpl implements MsgBatchInfoService {
	@Autowired
	MsgBatchInfoDao batchInfoDao;

	@Override
	public void updateSendNumber() {
		batchInfoDao.updateSendNumber(MsgConstant.MsgStatus.COMPLETE.getValue());
	}

	@Override
	public void save(MsgBatchInfoEntity batchInfo) {
		batchInfoDao.save(batchInfo);
	}

	@Override
	public void update(MsgBatchInfoEntity batchInfo) {
		batchInfoDao.update(batchInfo);
	}
	@Override
	public List<MsgBatchInfoEntity> queryList(Map<String, Object> map) {
		return batchInfoDao.queryList(map);
	}
	@Override
	public MsgBatchInfoEntity queryObject(String batchInfo_id) {
		return batchInfoDao.queryObject(batchInfo_id);
	}
	@Override
	public int queryTotal(Map<String, Object> map) {
		return batchInfoDao.queryTotal(map);
	}
	@Override
	public void deleteBatch(String[] batchInfo_idList) {
		batchInfoDao.deleteBatch(batchInfo_idList);
	}
}
