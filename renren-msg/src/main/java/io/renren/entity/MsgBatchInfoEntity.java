package io.renren.entity;

import java.io.Serializable;
import java.util.Date;

import io.renren.util.ShortUUID;

/**
 * 短信批次信息，通过任意途径，进行任意条短信发送都是一次投资
 * @author gaoyupeng
 * @date 2017年6月28日
 */
public class MsgBatchInfoEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String batchInfoId;
	private Long userId;
	private String username;
	private Date createTime;
	
	private int totalNumber;
	private int checkNumber;
	private int sendNumber;
	
	
	public MsgBatchInfoEntity() {
		super();
	}
	
	
	public MsgBatchInfoEntity(SysUserEntity user,int totalNumber) {
		super();
		this.batchInfoId=ShortUUID.generate();
		this.createTime = new Date();
		this.userId = user.getUserId();
		this.username = user.getUsername();
		this.totalNumber = totalNumber;
	}
	
	

	public int getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}
	public String getBatchInfoId() {
		return batchInfoId;
	}
	public Long getUserId() {
		return userId;
	}
	public String getUsername() {
		return username;
	}
	public Date getCreateTime() {
		return createTime;
	}
	
	public int getCheckNumber() {
		return checkNumber;
	}
	public int getSendNumber() {
		return sendNumber;
	}
	public void setBatchInfoId(String batchInfoId) {
		this.batchInfoId = batchInfoId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public void setCheckNumber(int checkNumber) {
		this.checkNumber = checkNumber;
	}
	public void setSendNumber(int sendNumber) {
		this.sendNumber = sendNumber;
	}
	
	
}
