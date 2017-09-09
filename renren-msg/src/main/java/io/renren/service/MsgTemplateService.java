package io.renren.service;

import java.util.List;
import java.util.Map;

import io.renren.entity.MsgTemplateEntity;

public interface MsgTemplateService {
	
	
	List<MsgTemplateEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String,Object> map);
	
	MsgTemplateEntity queryById(Long templateId);
	
	void save(MsgTemplateEntity template);
	
	void update(MsgTemplateEntity template);
	
	void deleteBatch(Long[] templateIdList);
	
}
