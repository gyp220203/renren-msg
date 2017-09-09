package io.renren.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.renren.dao.MsgChannelDao;
import io.renren.entity.ImplementEntity;
import io.renren.entity.MsgChannelEntity;
import io.renren.service.ImplementService;
import io.renren.service.MsgChannelService;
import io.renren.service.MsgSendService;
import io.renren.utils.SpringContextUtils;
/**
 * 
 * @author gaoyupeng
 * @date 2017年6月28日
 */
@Service("msgChannelService")
public class MsgChannelServiceImpl implements MsgChannelService{
	@Autowired
	MsgChannelDao channelDao;
	@Autowired
	ImplementService implementService;
	

	@Override
	public void updateBalance() {
		Map<String, Object> map = new HashMap<String,Object>();
		List<MsgChannelEntity> list = channelDao.queryList(map);
		for(MsgChannelEntity channel :list){
			if(channel.getImplementId()==null){
				continue;
			}
			ImplementEntity implement=implementService.queryById(channel.getImplementId());
			MsgSendService sendService=(MsgSendService) SpringContextUtils.getBean(implement.getBeanName());
			int balance =sendService.getBalance(channel);
			channelDao.updateBalance(channel.getChannelId(), balance);
		}
	}

	
	@Transactional
	@Override
	public void save(MsgChannelEntity channel) {
		channelDao.save(channel);
		//保存通道与用户关系
		for(Long userId :channel.getUserIdList()){
			channelDao.saveChannelUser(channel.getChannelId(),userId);
		}
	}
	@Transactional
	@Override
	public void update(MsgChannelEntity channel) {
		channelDao.update(channel);
		//清除原user关联
		channelDao.deleteChannelUser(channel.getChannelId());
		for(Long userId :channel.getUserIdList()){
			channelDao.saveChannelUser(channel.getChannelId(),userId);
		}
	}
	@Override
	public List<MsgChannelEntity> queryList(Map<String, Object> map) {
		List<MsgChannelEntity> list = channelDao.queryList(map);
		return list;
	}
	@Override
	public MsgChannelEntity queryById(Long id) {
		return channelDao.queryObject(id);
	}
	
	@Override
	public List<Long> queryChannelUserList(Long channelId) {
		return channelDao.queryChannelUserList(channelId);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return channelDao.queryTotal();
	}
	
	@Override
	public void delete(Long channelId) {
		channelDao.delete(channelId);
	}

	@Override
	@Transactional
	public void deleteBatch(Long[] channelIdList) {
		channelDao.deleteBatch(channelIdList);
	}


	

}
