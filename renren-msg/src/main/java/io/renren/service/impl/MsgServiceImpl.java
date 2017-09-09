package io.renren.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.renren.dao.MsgDao;
import io.renren.entity.MsgEntity;
import io.renren.service.MsgService;
@Service("msgService")
public class MsgServiceImpl implements MsgService {
	
	@Autowired
	MsgDao msgDao;
	
	@Override
	public void save(MsgEntity msg) {
		msg.setCreateTime(new Date());
		msgDao.save(msg);
	}
	
	@Override
	public void update(MsgEntity msg) {
		msgDao.update(msg);
	}
	@Override
	public List<MsgEntity> queryList(Map<String, Object> map) {
		return msgDao.queryList(map);
	}
	@Override
	public MsgEntity queryById(Long msgId){
		return msgDao.queryObject(msgId);
	}
	@Override
	public int queryByStatus(Map<String, Object> map) {
		return msgDao.queryByStatus(map);
	}
	@Override
	public int queryTotal(Map<String, Object> map) {
		return msgDao.queryTotal();
	}
	@Override
	public void deleteBatch(Long[] msgIdList) {
		msgDao.deleteBatch(msgIdList);
	}
	
	
	
	

}
