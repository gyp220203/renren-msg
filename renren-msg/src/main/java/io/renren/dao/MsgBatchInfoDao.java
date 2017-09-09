package io.renren.dao;

import io.renren.entity.MsgBatchInfoEntity;
/**
 * 
 * @author gaoyupeng
 * @date 2017年6月28日
 */
public interface MsgBatchInfoDao extends BaseDao<MsgBatchInfoEntity> {
	/**
	 * 更新发送条数
	 * @param status
	 */
	public void updateSendNumber(int status);
	
	
}
