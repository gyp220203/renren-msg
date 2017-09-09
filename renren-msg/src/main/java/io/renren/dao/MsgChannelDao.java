package io.renren.dao;

import java.util.List;
import io.renren.entity.MsgChannelEntity;
/**
 * 通道dao
 * @author gaoyupeng
 * @date 2017年6月20日
 */
public interface MsgChannelDao extends BaseDao<MsgChannelEntity>{
	/**
	 * 更新余额
	 * @param userId
	 * @return
	 */
	int updateBalance(Long channelId,int balance);
	/**
	 * 查询通道可用用户
	 * @param userID
	 * @return
	 */
	List<Long> queryChannelUserList(Long channelId);
	
	int saveChannelUser(Long channelId, Long userId);
	
	int deleteChannelUser(Long channelId);
	
}
