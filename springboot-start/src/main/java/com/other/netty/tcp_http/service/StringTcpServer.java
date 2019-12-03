package com.other.netty.tcp_http.service;
import com.other.netty.tcp_http.handler.StringTcpServerHandler;
import com.other.netty.template.NettyServiceTemplate;

import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class StringTcpServer extends NettyServiceTemplate {
	private int port = 8088;
	private String name = "String Server";

	// SpringBeanService springBeanService;

	public StringTcpServer(int port) {
		this.port = port;
	}

	// 可以启动server时将spring的bean传递进来。
	// public StringTcpServerTest(int port, SpringBeanService springBeanService)
	// {
	// this.port = port;
	// this.springBeanService = springBeanService;
	// }

	@Override
	protected ChannelHandler[] createHandlers() {
		return new ChannelHandler[] { 
				new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()),
				new StringDecoder(), 
				new StringEncoder(),

				// 如果想使用spring的bean，可以将springBeanService传递给StringTcpServerHandler，如new
				// StringTcpServerHandler(springBeanService)
				new StringTcpServerHandler() };
	}

	@Override
	public int getPort() {
		return port;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setName(String name) {
		this.name = name;
	}

}