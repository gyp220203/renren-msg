package io.renren.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * 短信实体
 * @author gaoyupeng
 * @date 2017年6月28日
 */
public class MsgEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String msgId;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 错误消息
	 */
	private String statusMsg;
	/**
	 * 电话号码
	 */
	private String telNumber;
	/**
	 * 模版id
	 */
	private Long templateId;
	/**
	 * 模版名称
	 */
	private String templateName;
	/**
	 * 通道id
	 */
	private Long channelId;
	/**
	 * 通道名称
	 */
	private String channelName;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 花费
	 */
	private int cost;
	/**
	 * 返回码
	 */
	private String responseCode;
	/**
	 * 返回消息
	 */
	private String responseMsg;
	/**
	 * 发送用户id
	 */
	private Long userId;
	/**
	 * 发送用户名
	 */
	private String userName;
	/**
	 * 类型（批量/单条）
	 */
	private Integer style;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 发送时间
	 */
	private Date sendTime;
	
	private MsgChannelEntity channel;
	
	private String batchInfoId;
	
	public String getBatchInfoId() {
		return batchInfoId;
	}

	public void setBatchInfoId(String batchInfoId) {
		this.batchInfoId = batchInfoId;
	}

	public MsgChannelEntity getChannel() {
		return channel;
	}

	public void setChannel(MsgChannelEntity channel) {
		this.channel = channel;
	}

	public String getMsgId() {
		return msgId;
	}

	public Integer getStatus() {
		return status;
	}

	public String getStatusMsg() {
		return statusMsg;
	}

	public String getTelNumber() {
		return telNumber;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public Long getChannelId() {
		return channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public String getContent() {
		return content;
	}

	public int getCost() {
		return cost;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public Long getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public Integer getStyle() {
		return style;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setStyle(Integer style) {
		this.style = style;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	@Override
	public String toString() {
		return "MsgEntity [msgId=" + msgId + ", status=" + status + ", statusMsg=" + statusMsg + ", telNumber="
				+ telNumber + ", templateId=" + templateId + ", templateName=" + templateName + ", channelId="
				+ channelId + ", channelName=" + channelName + ", content=" + content + ", cost=" + cost
				+ ", responseCode=" + responseCode + ", responseMsg=" + responseMsg + ", userId=" + userId
				+ ", userName=" + userName + ", style=" + style + ", createTime=" + createTime + ", sendTime="
				+ sendTime + ", channel=" + channel + ", batchInfoId=" + batchInfoId + "]";
	}
	

}
