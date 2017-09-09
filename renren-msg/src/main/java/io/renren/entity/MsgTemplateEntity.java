package io.renren.entity;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 短信通道模版
 * @author gaoyupeng
 * @date 2017年6月28日
 */
public class MsgTemplateEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 模版id
	 */
	private Long templateId;
	/**
	 * 模版名称
	 */
	@NotBlank(message="名称不能为空")
	private String templateName;
	/**
	 * 模版内容
	 */
	@NotBlank(message="模版不能为空")
	private String templateBody;
	/**
	 * 
	 */
	private Integer parameterNo;
	/**
	 * 
	 */
	@NotNull(message="必须绑定通道")
	private Long channelId;
	/**
	 * 
	 */
	private String signature;
	
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	public Long getTemplateId() {
		return templateId;
	}
	
	public String getTemplateName() {
		return templateName;
	}
	public String getTemplateBody() {
		return templateBody;
	}
	public Integer getParameterNo() {
		return parameterNo;
	}
	
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public void setTemplateBody(String templateBody) {
		this.templateBody = templateBody;
	}
	public void setParameterNo(Integer parameterNo) {
		this.parameterNo = parameterNo;
	}
	public Long getChannelId() {
		return channelId;
	}
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}
	
	
	
	
}
