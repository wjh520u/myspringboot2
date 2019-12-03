package com.wjh.netty.tcp_http.handler;

import com.wjh.netty.tcp_http.properties.response.HttpResponseMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class JsonHttpServerHandler extends SimpleChannelInboundHandler<String> {
	String charset = "UTF-8";

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		System.out.println("post内容:" + msg);
		HttpResponseMsg hrm = new HttpResponseMsg();
    	hrm.setResType(HttpResponseMsg.ResType.JSON.getValue());
    	hrm.setResCode(HttpResponseMsg.ResCode.OK.getValue());
    	hrm.setMessage(msg);
    	ctx.writeAndFlush(hrm);
	}

}