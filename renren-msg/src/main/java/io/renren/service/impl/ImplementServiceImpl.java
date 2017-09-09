package io.renren.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.renren.dao.ImplementDao;
import io.renren.entity.ImplementEntity;
import io.renren.service.ImplementService;
@Service("implementService")
public class ImplementServiceImpl implements ImplementService {
	
	@Autowired
	private ImplementDao implementDao;
	
	@Override
	public List<ImplementEntity> queryList(Map<String, Object> map) {
		return implementDao.queryList(map);
	}

	@Override
	public int queryTotal() {
		return implementDao.queryTotal();
	}

	@Override
	public ImplementEntity queryById(Long implementId) {
		return implementDao.queryObject(implementId);
	}

	@Override
	public void save(ImplementEntity implement) {
		implementDao.save(implement);

	}

	@Override
	public void update(ImplementEntity implement) {
		implementDao.update(implement);
	}

	@Override
	public void deleteBatch(Long[] implementIdList) {
		implementDao.deleteBatch(implementIdList);
	}

}
