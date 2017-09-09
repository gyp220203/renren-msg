package io.renren.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.renren.dao.MsgTemplateDao;
import io.renren.entity.MsgTemplateEntity;
import io.renren.service.MsgTemplateService;
import io.renren.util.MessageFormatUtil;
@Service("msgTemplateService")
public class MsgTemplateServiceImpl implements MsgTemplateService {
	@Autowired
	MsgTemplateDao templateDao;
	
	@Override
	public List<MsgTemplateEntity> queryList(Map<String, Object> map) {
		return templateDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return templateDao.queryTotal(map);
	}

	@Override
	public MsgTemplateEntity queryById(Long templateId) {
		return templateDao.queryObject(templateId);
	}

	@Override
	public void save(MsgTemplateEntity template) {
		template.setParameterNo(MessageFormatUtil.getParameterNo(template.getTemplateBody()));
		templateDao.save(template);
	}

	@Override
	public void update(MsgTemplateEntity template) {
		template.setParameterNo(MessageFormatUtil.getParameterNo(template.getTemplateBody()));
		templateDao.update(template);
	}


	@Override
	public void deleteBatch(Long[] templateIdList) {
		templateDao.deleteBatch(templateIdList);
	}


}
