package cn.efunding.fund.url.http;

import java.net.URL;

import cn.efunding.fund.url.IGet;

public class HttpGet extends HttpMessage implements IGet {

	public HttpGet(URL url) throws Throwable{
		super(url);
		conn.setRequestMethod("GET");
		conn.setDoOutput(false);
		conn.setDoInput(true);
	}
	
}
