package io.renren.service;

import java.util.List;
import java.util.Map;

import io.renren.entity.ImplementEntity;

public interface ImplementService {
	List<ImplementEntity> queryList(Map<String, Object> map);

	int queryTotal();

	ImplementEntity queryById(Long implementId);

	void save(ImplementEntity implement);

	void update(ImplementEntity implement);

	void deleteBatch(Long[] implementIdList);
}
