package test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * 传输对象
 * @author gaoyupeng 2017/06/28
 *
 */
public class MsgTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long userId;
	private String password;
	private transient String url;
	private ArrayList<MsgEntity> msgList=new ArrayList<MsgEntity>();
	/**
	 * 构造传输对象
	 * @param userId 用户id（需在短信平台配置）
	 * @param password 密码（需在短信平台配置）
	 * @param url 请求地址
	 */
	public MsgTO(Long userId, String password, String url) {
		super();
		this.userId = userId;
		this.password = password;
		this.url = url;
	}
	/**
	 * 添加短信
	 * @param telNumber 电话号码
	 * @param templateId 模版ID（请登录短信平台配置）
	 * @param content 短信内容
	 * @param sendTime 发送时间
	 */
	public void addEntity(String telNumber, Long templateId, String content, Date sendTime){
		msgList.add(new MsgEntity(telNumber,templateId,content,sendTime));
	}
	@SuppressWarnings("unused")
	private class MsgEntity implements Serializable{
		private static final long serialVersionUID = 1L;
		/**
		 * 电话号码
		 */
		private String telNumber;
		/**
		 * 模版id
		 */
		private Long templateId;
		
		/**
		 * 内容
		 */
		private String content;
		/**
		 * 发送时间
		 */
		private Date sendTime;
		public MsgEntity(String telNumber, Long templateId, String content, Date sendTime) {
			super();
			this.telNumber = telNumber;
			this.templateId = templateId;
			this.content = content;
			this.sendTime = sendTime;
		}
		public String getTelNumber() {
			return telNumber;
		}
		public Long getTemplateId() {
			return templateId;
		}
		public String getContent() {
			return content;
		}
		public Date getSendTime() {
			return sendTime;
		}
	}
	
	public Long getUserId() {
		return userId;
	}
	public String getPassword() {
		return password;
	}
	public String getUrl() {
		return url;
	}
	public ArrayList<MsgEntity> getMsgList() {
		return msgList;
	}
	
	
	
}
