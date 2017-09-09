package io.renren.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.renren.annotation.SysLog;
import io.renren.entity.MsgBatchInfoEntity;
import io.renren.entity.MsgEntity;
import io.renren.entity.SysUserEntity;
import io.renren.service.MsgBatchInfoService;
import io.renren.service.MsgPrepareService;
import io.renren.service.MsgService;
import io.renren.service.ParseExcelService;
import io.renren.util.ExcelDownloadUtil;
import io.renren.utils.Constant;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;
import io.renren.utils.RRException;
/**
 * 短信批量信息，批量上传controller
 * @author gaoyupeng
 * @date 2017年6月31日
 */
@RestController
@RequestMapping("msg/batchInfo")
public class MsgBatchInfoController extends AbstractController{
	@Autowired
	private MsgBatchInfoService batchInfoService;
	@Autowired
	private MsgService msgService;
	@Autowired
	private ParseExcelService parseExcelService;
	@Autowired
	MsgPrepareService msgPrepareService;
	
	@RequestMapping("/list")
	@RequiresPermissions("msg:batchInfo:list")
	public R list(@RequestParam Map<String,Object> params){
		//只有管理员能查看全部通道
		if(getUserId() != Constant.SUPER_ADMIN){
			params.put("userId", getUserId());
		}
		Query query = new  Query(params);
		List<MsgBatchInfoEntity> batchInfoList = batchInfoService.queryList(query);
		int total = batchInfoService.queryTotal(query);
		PageUtils pageUtil = new PageUtils(batchInfoList,total,query.getLimit(),query.getPage());
		return R.ok().put("page", pageUtil);
	}
	/**
	 * 模版信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/info/{batchInfoId}")
	@RequiresPermissions("msg:batchInfo:info")
	//从路径中取变量@PathVariable 看上面的requestMapping，指的就是后面那个同名参数
	public R info(@RequestParam Map<String,Object> params,@PathVariable("batchInfoId")String batchInfoId){
		params.put("batchInfoId", batchInfoId);
		Query query = new  Query(params);
		List<MsgEntity> msgList=msgService.queryList(query);
		int total = msgService.queryTotal(params);
		PageUtils pageUtil = new PageUtils(msgList,total,query.getLimit(),query.getPage());
		return R.ok().put("page", pageUtil);
	}
	@SysLog("删除批量短信记录")
	@RequestMapping("/delete")
	@RequiresPermissions("msg:batchInfo:delete")
	public R delete(@RequestBody String[] templateId){
		batchInfoService.deleteBatch(templateId);
		return R.ok();
	}
	/**
	 * 上传文件
	 */
	@SysLog("excel上传")
	@RequestMapping("/upload")
	@RequiresPermissions("msg:batchInfo:upload")
	public R upload(@RequestParam("file") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (file.isEmpty()) {
			throw new RRException("上传文件不能为空");
		}
		String filePath = request.getSession().getServletContext().getRealPath(File.separator + "upload")+File.separator;
		String fileName = file.getOriginalFilename()+"_"+super.getUserId()+"_"+new Date().getTime();
		File targetFile = new File(filePath, fileName);
		file.transferTo(targetFile);
		ArrayList<MsgEntity> msgList =parseExcelService.getMsgListFromExcel(targetFile);
		if(msgList.size()==0){
			return R.error("您上传的文件中没有数据");
		}
		MsgBatchInfoEntity batchInfo = new MsgBatchInfoEntity(super.getUser(),msgList.size());
		initList(super.getUser(),batchInfo.getBatchInfoId(),msgList);
		int checkNumber=batchSave(batchInfo,msgList);
		return R.ok("成功上传"+msgList.size()+"条，通过校验"+checkNumber+"条");
	}
	@Transactional
	public int batchSave(MsgBatchInfoEntity batchInfo,ArrayList<MsgEntity> msgList){
		batchInfoService.save(batchInfo);//保存批量信息
		int checkNumber=msgPrepareService.batchSaveMsg(msgList);
		batchInfo.setCheckNumber(checkNumber);
		batchInfoService.update(batchInfo);//保存批量信息
		return checkNumber;
	}
	@RequestMapping("/download")
	@RequiresPermissions("msg:batchInfo:download")
	 public void downLoadExcelModel(HttpServletRequest request,HttpServletResponse response) throws Exception {
        String download = request.getSession().getServletContext().getRealPath(File.separator+"download")+File.separator; //获取下载路劲
        ExcelDownloadUtil.downLoadFile("短信模版.xls","xls",download, response);//依次传入需要下载的文件名，文件格式，路径，response参数
     }
	private void initList(SysUserEntity user, String batchInfoId ,ArrayList<MsgEntity> msgList){
		for(MsgEntity msg :msgList){
			msg.setBatchInfoId(batchInfoId);
			msg.setUserId(user.getUserId());
			msg.setUserName(user.getUsername());
		}
	}
	
	
}
