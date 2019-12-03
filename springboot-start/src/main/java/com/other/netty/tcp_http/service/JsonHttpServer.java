package com.other.netty.tcp_http.service;
import com.other.netty.tcp_http.decoder.HttpMsgRequestDecoder;
import com.other.netty.tcp_http.encoder.HttpMsgResponseEncoder;
import com.other.netty.tcp_http.handler.JsonHttpServerHandler;
import com.other.netty.template.NettyServiceTemplate;

import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class JsonHttpServer extends NettyServiceTemplate {
	int port = 8888;
	String name = "Json Server";
	private String charset = "UTF-8";
	private int timeout = 60;
	
	public JsonHttpServer(int port) {
		this.port = port;
	}

	@Override
	protected ChannelHandler[] createHandlers() {
		return new ChannelHandler[] { 
				new HttpResponseEncoder(), 
				new HttpRequestDecoder(),
				new HttpObjectAggregator(1048576), 
				new HttpMsgResponseEncoder(charset, timeout),
				new HttpMsgRequestDecoder(charset), 
				new JsonHttpServerHandler() };
	}

	@Override
	public int getPort() {
		return port;
	}

	@Override
	public String getName() {
		return name;
	}

}