package io.renren.util;
/**
 * 短信类常量
 * @author gaoyupeng
 *
 */
public class MsgConstant {
	public enum MsgStatus {
		INIT(0),
		/**
		 * 待发送
		 */
		WAIT(1),
		/**
		 * 完成
		 */
		COMPLETE(2),
		/**
		 * 错误
		 */
		ERROR(9);
		private int value;
		private MsgStatus(int value){
			this.value =value;
		}
		public int getValue() {
            return value;
        }
	}
	
	public enum MsgStyle{
		/**
		 * 单条
		 */
		SINGLE(0),
		/**
		 * 批量
		 */
		BATCH(1);
		private int value;
		private MsgStyle(int value){
			this.value = value;
		}
		public int getValue(){
			return value;
		}
	}
	public enum MsgChannelStyle{
		/**
		 * 启用
		 */
		ON(1),
		/**
		 * 禁用
		 */
		OFF(0);
		private int value ;
		private MsgChannelStyle(int value){
			this.value =value;
		}
		public int getValue(){
			return value;
		}
	}
}
