package com.other.netty.tcp_http.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class StringTcpServerHandler extends SimpleChannelInboundHandler<String> {
	String charset = "UTF-8";

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		System.out.println("内容:" + msg);
		ctx.writeAndFlush("返回内容：" + msg);
	}
}