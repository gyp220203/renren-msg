package io.renren.controller;

import io.renren.annotation.SysLog;
import io.renren.entity.MsgChannelEntity;
import io.renren.entity.SysUserEntity;
import io.renren.service.MsgChannelService;
import io.renren.service.MsgPrepareService;
import io.renren.service.SysUserService;
import io.renren.utils.Constant;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;
import io.renren.validator.ValidatorUtils;

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
/**
 * 短信通道controller
 * @author gaoyupeng
 * @date 2017年6月31日
 */
//@RestControllerAdvice
@RestController
@RequestMapping("/msg/channel")
public class MsgChannelController extends AbstractController{
	@Autowired
	private MsgChannelService channelService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private MsgPrepareService msgPrepareService;
	/**
	 * 通道列表
	 * @param params
	 * @return
	 */
	@RequestMapping("/list")
	@RequiresPermissions("msg:channel:list")
	public R list(@RequestParam Map<String,Object> params){
		//只有管理员能查看全部通道
		if(getUserId() != Constant.SUPER_ADMIN){
			params.put("userId", getUserId());
		}
		Query query = new  Query(params);
		List<MsgChannelEntity> channelList = channelService.queryList(query);
		int total = channelService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(channelList,total,query.getLimit(),query.getPage());
		return R.ok().put("page", pageUtil);
	}
	/**
	 * 通道用户列表
	 */
	@RequestMapping("/selectUser")
	@RequiresPermissions("msg:channel:selectUser")
	public R selectUser(){
		Map<String, Object> map = new HashMap<>();
		if(getUserId() != Constant.SUPER_ADMIN){
			map.put("createUserId", getUserId());
		}
		List<SysUserEntity> userList = sysUserService.queryList(map);
		return R.ok().put("list", userList);
	}
	/**
	 * 通道列表
	 */
	@RequestMapping("/select")
	@RequiresPermissions("msg:channel:select")
	public R select(){
		Map<String, Object> map = new HashMap<>();
		if(getUserId() != Constant.SUPER_ADMIN){
			map.put("userId", getUserId());
		}
		List<MsgChannelEntity> channelList = channelService.queryList(map);
		return R.ok().put("list", channelList);
	}
	/**
	 * 通道信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/info/{channelId}")
	@RequiresPermissions("msg:channel:info")
	//从路径中取变量@PathVariable 看上面的requestMapping，指的就是后面那个同名参数
	public R info(@PathVariable("channelId")Long channelId){
		MsgChannelEntity channel = channelService.queryById(channelId);
		//加入用户分配表
		List<Long> userList =channelService.queryChannelUserList(channelId);
		channel.setUserIdList(userList);
		return R.ok().put("channel", channel);
	}
	
	/**
	 * 保存通道
	 * @param channel
	 * @return
	 */
	@SysLog("新增通道")
	@RequestMapping("/save")
	@RequiresPermissions("msg:channel:save")
	public R save(@RequestBody MsgChannelEntity channel){
		ValidatorUtils.validateEntity(channel);
		channelService.save(channel);
		return R.ok();
	}
	@SysLog("修改通道")
	@RequestMapping("/update")
	@RequiresPermissions("msg:channel:update")
	public R update(@RequestBody MsgChannelEntity channel){
		ValidatorUtils.validateEntity(channel);
		channelService.update(channel);
		msgPrepareService.clearMap();
		return R.ok();
	}
	@SysLog("删除通道")
	@RequestMapping("/delete")
	@RequiresPermissions("msg:channel:delete")
	public R delete(@RequestBody Long[] channelId){
		channelService.deleteBatch(channelId);
		return R.ok();
	}
	
	@RequestMapping("/updateBalance")
	@RequiresPermissions("msg:channel:updateBalance")
	public R updateBalance(){
		channelService.updateBalance();
		return R.ok();
	}
	
}
