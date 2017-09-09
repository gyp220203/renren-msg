package io.renren.entity;

import java.io.Serializable;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import io.renren.validator.group.AddGroup;
import io.renren.validator.group.UpdateGroup;
/**
 * 短信通道
 * @author gaoyupeng
 * @date 2017年6月28日
 */
public class MsgChannelEntity implements Serializable{
	private static final long serialVersionUID=1L;
	/**
	 * 通道ID
	 */
	private Long channelId;
	/**
	 * 通道名称
	 */
	@NotBlank(message="通道名不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String channelName;
	/**
	 * 状态1：启用 0：禁用
	 */
	private Integer status;
	/**
	 * 登录名
	 */
	@NotBlank(message="登录名不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String username;
	/**
	 * 登录密码
	 */
	@NotBlank(message="登录密码不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String password;
	/**
	 * 短信发送url
	 */
	private String url4send;
	/**
	 * 短信查询url
	 */
	private String url4query;
	/**
	 * 账户余额
	 */
	private Integer balance;
	/**
	 * 用户列表
	 */
	private List<Long> userIdList;
	/**
	 * 实现类Id
	 */
	private Long implementId;
	
	/**
	 * 签名
	 */
	private String signature;
	
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public Long getImplementId() {
		return implementId;
	}
	public void setImplementId(Long implementId) {
		this.implementId = implementId;
	}
	public List<Long> getUserIdList() {
		return userIdList;
	}
	public void setUserIdList(List<Long> userList) {
		this.userIdList = userList;
	}
	
	public Long getChannelId() {
		return channelId;
	}
	public String getChannelName() {
		return channelName;
	}
	public Integer getStatus() {
		return status;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getUrl4send() {
		return url4send;
	}
	public String getUrl4query() {
		return url4query;
	}
	public Integer getBalance() {
		return balance;
	}
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setUrl4send(String url4send) {
		this.url4send = url4send;
	}
	public void setUrl4query(String url4query) {
		this.url4query = url4query;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	
}
