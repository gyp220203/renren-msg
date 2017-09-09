package io.renren.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 传输对象
 * @author gaoyupeng
 * @date 2017年6月28日
 */
public class MsgTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long userId;
	private String password;
	private transient SysUserEntity user;
	private ArrayList<MsgEntity> msgList;
	public Long getUserId() {
		return userId;
	}
	public String getPassword() {
		return password;
	}
	public SysUserEntity getUser() {
		return user;
	}
	public ArrayList<MsgEntity> getMsgList() {
		return msgList;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setUser(SysUserEntity user) {
		this.user = user;
	}
	public void setMsgList(ArrayList<MsgEntity> msgList) {
		this.msgList = msgList;
	}
	
	
	
}
