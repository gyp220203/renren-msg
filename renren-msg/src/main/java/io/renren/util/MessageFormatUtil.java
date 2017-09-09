package io.renren.util;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.renren.utils.RRException;

public class MessageFormatUtil {
	public static String format(String msg,Object[] arry){
		return MessageFormat.format(msg, arry);
	}
	public static int getParameterNo(String str){
		String regEx ="\\{\\d+\\}";
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(str);
		int count=0;
		while(mat.find()){
			count++;
		}
		for(int i=0 ;i<count;i++){
			if(!str.contains("{"+i+"}")){
				throw new RRException("模版表达式错误：缺少{"+i+"}");
			}
		}
		return count;
	}
	public static void main(String[] args) {
		System.out.println(format("abcde{0}hjk",new Object []{"!","@"}));
	}
}
