package io.renren.msgApi;

import java.util.ArrayList;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.renren.annotation.SysLog;
import io.renren.entity.MsgBatchInfoEntity;
import io.renren.entity.MsgEntity;
import io.renren.entity.MsgTO;
import io.renren.entity.SysUserEntity;
import io.renren.service.ImplementService;
import io.renren.service.MsgBatchInfoService;
import io.renren.service.MsgPrepareService;
import io.renren.service.MsgSendService;
import io.renren.service.MsgService;
import io.renren.service.SysUserService;
import io.renren.util.MsgConstant;
import io.renren.utils.R;
import io.renren.utils.RRException;
import io.renren.utils.ShiroUtils;
import io.renren.utils.SpringContextUtils;

@RestController
@RequestMapping("/msg/send")
public class MsgSendController {
	@Autowired
	MsgService msgService;
	@Autowired
	SysUserService sysUserService;
	@Autowired
	MsgPrepareService msgPrepareService;
	@Autowired
	ImplementService implementService;
	@Autowired
	MsgBatchInfoService batchInfoService;

	@SysLog("接口请求")
	@RequestMapping(value = "/api", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public R api(@ModelAttribute("login") MsgTO msgTo) {
		String res = "";
		ArrayList<MsgEntity> msgList = msgTo.getMsgList();
		if (msgList == null || msgList.size() == 0) {
			return R.error("上传列表为空");
		}
		MsgBatchInfoEntity batchInfo = new MsgBatchInfoEntity(msgTo.getUser(), msgList.size());
		initList(msgTo.getUser(), batchInfo.getBatchInfoId(), msgList);
		batchInfoService.save(batchInfo);// 保存批量信息
		if (msgList.size() == 1 && msgList.get(0) != null) {
			msgList.get(0).setStyle(MsgConstant.MsgStyle.SINGLE.getValue());
			MsgEntity msg = msgPrepareService.saveMsg(msgList.get(0));
			if (MsgConstant.MsgStatus.WAIT.getValue() != msg.getStatus()) {
				return R.error("上传短信未提交,异常信息:" + msg.getStatusMsg());
			}
			batchInfo.setCheckNumber(1);
			batchInfoService.update(batchInfo);
			// 单条校验无误后直接发送
			String beanName = implementService.queryById(msg.getChannel().getImplementId()).getBeanName();
			MsgSendService msgSendService = (MsgSendService) SpringContextUtils.getBean(beanName);
			if (msgSendService == null) {
				return R.error("上传短信未提交,异常信息:发送服务实现类未找到");
			}
			msgSendService.singleSend(msg);
			msgService.update(msg);// 发送后需要update短信的信息
			if (MsgConstant.MsgStatus.COMPLETE.getValue() == msg.getStatus()) {
				batchInfo.setSendNumber(1);
				batchInfoService.update(batchInfo);
				res = "成功上传并提交1条短信";
			} else {
				return R.error("上传短信未提交,异常信息:" + msg.getStatusMsg() + "," + msg.getResponseMsg());
			}

		}
		if (msgList.size() > 1) {
			// 批量只校验存储，不发送
			int checkNumber = msgPrepareService.batchSaveMsg(msgList);
			batchInfo.setCheckNumber(checkNumber);
			batchInfoService.update(batchInfo);// 保存批量信息
			res = "成功上传" + msgList.size() + "条信息，通过校验" + checkNumber + "条";
		}
		return R.ok(res);

	}

	private void initList(SysUserEntity user, String batchInfoId, ArrayList<MsgEntity> msgList) {
		for (MsgEntity msg : msgList) {
			if (msg == null) {
				continue;
			}
			msg.setBatchInfoId(batchInfoId);
			msg.setUserId(user.getUserId());
			msg.setUserName(user.getUsername());
		}
	}

	@ModelAttribute("login")
	private MsgTO login(@RequestBody MsgTO msgTo) {
		Long userId = msgTo.getUserId();
		String password = msgTo.getPassword();
		SysUserEntity user = ((SysUserService) SpringContextUtils.getBean("sysUserService")).queryObject(userId);
		if(user==null){
			throw new RRException("用户id不存在");
		}
		try {
			Subject subject = ShiroUtils.getSubject();
			// sha256加密
			password = new Sha256Hash(password).toHex();
			UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), password);
			subject.login(token);
		} catch (Exception e) {
			throw new RRException("登录失败");
		}
		msgTo.setUser(user);
		return msgTo;
	}

	
}
