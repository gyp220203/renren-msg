package io.renren.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.renren.entity.ImplementEntity;
import io.renren.entity.MsgChannelEntity;
import io.renren.entity.MsgEntity;
import io.renren.service.ImplementService;
import io.renren.service.MsgBatchInfoService;
import io.renren.service.MsgChannelService;
import io.renren.service.MsgSendService;
import io.renren.service.MsgService;
import io.renren.util.MsgConstant;
import io.renren.utils.SpringContextUtils;

@Component("batchSendTask")
public class BatchSendTask {
	@Autowired
	ImplementService implementService;
	@Autowired
	MsgChannelService msgChannelService;
	@Autowired
	MsgService msgService;
	@Autowired
	MsgBatchInfoService msgBatchInfoService;
	
	/**
	 * 自动批量任务任务,注意,该方法具有线程锁,避免并发时对短信的重复发送
	 */
	public synchronized void batchSend(){
		Map<String, Object> implementMap = new HashMap<String,Object>();
		List<ImplementEntity> implementList =implementService.queryList(implementMap);
		if(implementList.size()==0){return;}
		
		Map<String, Object> map = new HashMap<String,Object>();//实现约束map
		map.put("status", MsgConstant.MsgStatus.WAIT.getValue());//状态为待发送
		map.put("style", MsgConstant.MsgStyle.BATCH.getValue());//批量上传
		if(msgService.queryByStatus(map)==0){return;}//查询带发送短信数量
		
		final ExecutorService pool = Executors.newFixedThreadPool(5);//线程池
		
		Map<String, Object> channelMap = new HashMap<String,Object>();//通道约束map
		channelMap.put("status", MsgConstant.MsgChannelStyle.ON.getValue());//通道状态为启用
		for(ImplementEntity impl:implementList){
			channelMap.put("implementId", impl.getImplementId());
			List<MsgChannelEntity> channelList =msgChannelService.queryList(channelMap);//通道列表
			if(channelList.size()==0){continue;}
			final MsgSendService msgSendService=(MsgSendService) SpringContextUtils.getBean(impl.getBeanName());
			if(msgSendService ==null){continue;}
			Map<String, Object> msgMap = new HashMap<String,Object>();//短信约束map
			msgMap.put("style", MsgConstant.MsgStyle.BATCH.getValue());//批量短信
			msgMap.put("status", MsgConstant.MsgStatus.WAIT.getValue());//状态为待发送
			msgMap.put("sidx", "createTime");//创建时间排序
			msgMap.put("order", "asc");
			for(MsgChannelEntity channel:channelList){
				msgMap.put("channelId", channel.getChannelId());//put方法遇到相同key会直接替换value
				List<MsgEntity> msgList=msgService.queryList(msgMap);
				if(msgList.size()==0){continue;}
				
				for(final MsgEntity msg:msgList){
					msg.setChannel(channel);
					pool.execute(new Thread(new Runnable() {
						@Override
						public void run() {
							msgSendService.singleSend(msg);
							msgService.update(msg);
						}
					}));
				}
			}
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				pool.shutdown();//该方法不会马上关闭线程，但是会拒绝新线程的注册。
				while (!pool.isTerminated()){   
				}//监听是否所有线程已经结束
//				System.out.println("isTerminated");
				msgBatchInfoService.updateSendNumber();//更新数据
			}
		}).start();
		
	}
	
}
