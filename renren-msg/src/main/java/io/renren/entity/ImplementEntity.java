package io.renren.entity;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 短信通道实现类信息
 * @author gaoyupeng
 *
 */
public class ImplementEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String implementId;
	/**
	 * 通道实现名
	 */
	@NotBlank(message="名称不能为空")
	private String implementName;
	/**
	 * 通道实现service的BeanId
	 */
	@NotBlank(message="beanID不能为空")
	private String beanName;
	/**
	 * 实现描述
	 */
	private String describe;
	
	public String getImplementId() {
		return implementId;
	}
	public String getImplementName() {
		return implementName;
	}
	public String getBeanName() {
		return beanName;
	}
	public String getDescribe() {
		return describe;
	}
	public void setImplementId(String implementId) {
		this.implementId = implementId;
	}
	public void setImplementName(String implementName) {
		this.implementName = implementName;
	}
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
}
