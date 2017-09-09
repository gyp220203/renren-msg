package test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import com.alibaba.fastjson.JSON;

public class HttpPostMsg {
	public static String RequestJsonPost(MsgTO msgto) {
		String json = JSON.toJSONString(msgto);
		String strResponse = null;
		try {
			URL url1 = new URL(msgto.getUrl());
			HttpURLConnection conn = (HttpURLConnection) url1.openConnection();// 建立http连接
			conn.setDoOutput(true);// 设置允许输出
			conn.setDoInput(true);
			conn.setUseCaches(false);// 设置不用缓存
			conn.setRequestMethod("POST"); // 设置传递方式
			conn.setRequestProperty("Connection", "Keep-Alive");// 设置维持长连接
			conn.setRequestProperty("Charset", "UTF-8");// 设置文件字符集
			byte[] data = json.getBytes();// 转换为字节数组
			conn.setRequestProperty("Content-Length", String.valueOf(data.length));// 设置文件长度
			conn.setRequestProperty("Content-Type", "application/json");// 设置文件类型
			conn.connect(); // 开始连接请求
			OutputStream out = conn.getOutputStream();
			out.write(json.getBytes("utf-8"));// 写入请求的字符串
			out.flush();
			out.close();
			// System.out.println(conn.getResponseCode());
			// 请求返回的状态
			if (conn.getResponseCode() == 200) {
				// System.out.println("连接成功");
				InputStream in = conn.getInputStream();// 请求返回的数据
				try {
					byte[] data1 = new byte[in.available()];
					in.read(data1);
					strResponse = new String(data1, "utf-8");// 转成字符串
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else {
				System.out.println("no++");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strResponse;
	}

	public static void main(String[] args) {

		String test = "\\|";
		String[] testlist = test.split("\\|");
		System.out.println(Arrays.asList(testlist).toString() + "   " + testlist.length);

		MsgTO msgto = new MsgTO(1L, "123654", "http://localhost:8080/renren-web/msg/send/api");
		msgto.addEntity("15504400427", 5L, "3", null);
		String res = HttpPostMsg.RequestJsonPost(msgto);
		System.out.println(res);

		// msgto.addEntity("15504400427", 1L,
		// "http群发接口3,第一次发送短信验证，字数验证,短信验证需要注意签名长度,短信验证需要注意签名长度", null);
		// msgto.addEntity("18543116556", 1L, "http群发接口1", null);
		// msgto.addEntity("15526837882", 1L, "http群发接口2", null);

	}

}
