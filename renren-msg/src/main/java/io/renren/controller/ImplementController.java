package io.renren.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.annotation.SysLog;
import io.renren.entity.ImplementEntity;
import io.renren.service.ImplementService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;
import io.renren.validator.ValidatorUtils;
/**
 * 短信服务实现controller
 * @author gaoyupeng
 * @date 2017年6月31日
 */
@RestController
@RequestMapping("/msg/implement")
public class ImplementController {
	@Autowired
	private ImplementService implementService;
	
	/**
	 * 实现类列表
	 * @param params
	 * @return
	 */
	@RequestMapping("/list")
	@RequiresPermissions("msg:implement:list")
	public R list(@RequestParam Map<String,Object> params){
		Query query = new  Query(params);
		List<ImplementEntity> implementList = implementService.queryList(query);
		int total = implementService.queryTotal();
		PageUtils pageUtil = new PageUtils(implementList,total,query.getLimit(),query.getPage());
		return R.ok().put("page", pageUtil);
	}
	/**
	 * 通道信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/info/{implementId}")
	@RequiresPermissions("msg:implement:info")
	//从路径中取变量@PathVariable 看上面的requestMapping，指的就是后面那个同名参数
	public R info(@PathVariable("implementId")Long implementId){
		ImplementEntity implement = implementService.queryById(implementId);
		return R.ok().put("implement", implement);
	}
	/**
	 * 保存通道
	 * @param channel
	 * @return
	 */
	@SysLog("新增短信服务")
	@RequestMapping("/save")
	@RequiresPermissions("msg:implement:save")
	public R save(@RequestBody ImplementEntity implement){
		ValidatorUtils.validateEntity(implement);
		implementService.save(implement);
		return R.ok();
	}
	@SysLog("修改短信服务")
	@RequestMapping("/update")
	@RequiresPermissions("msg:implement:update")
	public R update(@RequestBody ImplementEntity implement){
		ValidatorUtils.validateEntity(implement);
		implementService.update(implement);
		return R.ok();
	}
	@SysLog("删除短信服务")
	@RequestMapping("/delete")
	@RequiresPermissions("msg:implement:delete")
	public R delete(@RequestBody Long[] implementId){
		implementService.deleteBatch(implementId);
		return R.ok();
	}
	/**
	 * 通道列表
	 */
	@RequestMapping("/select")
	@RequiresPermissions("msg:implement:select")
	public R select(){
		Map<String, Object> map = new HashMap<>();
		List<ImplementEntity> implementList = implementService.queryList(map);
		return R.ok().put("list", implementList);
	}
	
	
	
}
