package cn.efunding.fund.url;

import java.net.URL;

import cn.efunding.fund.url.http.HttpGet;
import cn.efunding.fund.url.http.HttpPost;
import cn.efunding.fund.url.http.HttpPut;

public class CURL {

	public static IGet get(URL url) throws Throwable{
		
		if("http".equals(url.getProtocol())){
			return new HttpGet(url);
		}
		
		if("https".equals(url.getProtocol())){
			return new HttpGet(url);
		}
		
		throw new Exception("不支持的协议 "+url.getProtocol());
	}
	
	public static IPost post(URL url) throws Throwable{
		
		if("http".equals(url.getProtocol())){
			return new HttpPost(url);
		}
		
		if("https".equals(url.getProtocol())){
			return new HttpPost(url);
		}
		
		throw new Exception("不支持的协议 "+url.getProtocol());
	}
	
	public static IPut put(URL url) throws Throwable{
		
		if("http".equals(url.getProtocol())){
			return new HttpPut(url);
		}
		
		if("https".equals(url.getProtocol())){
			return new HttpPut(url);
		}
		
		throw new Exception("不支持的协议 "+url.getProtocol());
	}
}
