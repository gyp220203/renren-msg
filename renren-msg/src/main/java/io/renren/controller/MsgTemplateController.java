package io.renren.controller;

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
import io.renren.entity.MsgTemplateEntity;
import io.renren.service.MsgPrepareService;
import io.renren.service.MsgTemplateService;
import io.renren.utils.Constant;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;
import io.renren.validator.ValidatorUtils;
/**
 * 短信模版controller
 * @author gaoyupeng
 * @date 2017年6月31日
 */
@RestController
@RequestMapping("msg/template")
public class MsgTemplateController extends AbstractController{
	@Autowired
	private MsgTemplateService templateService;
	
	@Autowired
	private MsgPrepareService msgPrepareService;
	
	@RequestMapping("/list")
	@RequiresPermissions("msg:template:list")
	public R list(@RequestParam Map<String,Object> params){
		//只有管理员能查看全部通道
		if(getUserId() != Constant.SUPER_ADMIN){
			params.put("userId", getUserId());
		}
		Query query = new  Query(params);
		List<MsgTemplateEntity> templateList = templateService.queryList(query);
		int total = templateService.queryTotal(query);
		PageUtils pageUtil = new PageUtils(templateList,total,query.getLimit(),query.getPage());
		return R.ok().put("page", pageUtil);
	}
	/**
	 * 模版信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/info/{templateId}")
	@RequiresPermissions("msg:template:info")
	//从路径中取变量@PathVariable 看上面的requestMapping，指的就是后面那个同名参数
	public R info(@PathVariable("templateId")Long templateId){
		MsgTemplateEntity template = templateService.queryById(templateId);
		return R.ok().put("template", template);
	}
	
	/**
	 * 保存通道
	 * @param 
	 * @return
	 */
	@SysLog("新增模版")
	@RequestMapping("/save")
	@RequiresPermissions("msg:template:save")
	public R save(@RequestBody MsgTemplateEntity template){
		ValidatorUtils.validateEntity(template);
		templateService.save(template);
		return R.ok();
	}
	@SysLog("修改模版")
	@RequestMapping("/update")
	@RequiresPermissions("msg:template:update")
	public R update(@RequestBody MsgTemplateEntity template){
		ValidatorUtils.validateEntity(template);
		templateService.update(template);
		msgPrepareService.clearMap();
		return R.ok();
	}
	@SysLog("删除模版")
	@RequestMapping("/delete")
	@RequiresPermissions("msg:template:delete")
	public R delete(@RequestBody Long[] templateId){
		
		try {
			templateService.deleteBatch(templateId);
			return R.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("通道删除异常，请先检查对应模版是否删除");
		}
	}
	
	
	
}
