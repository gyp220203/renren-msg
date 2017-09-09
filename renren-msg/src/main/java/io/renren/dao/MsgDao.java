package io.renren.dao;

import java.util.Map;

import io.renren.entity.MsgEntity;
/**
 * 短信dao
 * @author gaoyupeng
 * @date 2017年6月20日
 */
public interface MsgDao extends BaseDao<MsgEntity> {
	
	public int queryByStatus(Map<String,Object> map);
}
