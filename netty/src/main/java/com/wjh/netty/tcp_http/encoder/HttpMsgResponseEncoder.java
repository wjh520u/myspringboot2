package com.wjh.netty.tcp_http.encoder;

import java.util.List;


import com.wjh.netty.tcp_http.properties.response.HttpResponseMsg;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class HttpMsgResponseEncoder extends MessageToMessageEncoder<HttpResponseMsg> {
	private String charset;
	private int timeout;

	public HttpMsgResponseEncoder(String charset, int timeout) {
		super();
		this.charset = charset;
		this.timeout = timeout;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, HttpResponseMsg message, List<Object> out) {
		try {
			DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.valueOf(message.getResCode()),
					Unpooled.wrappedBuffer(message.getMessage().getBytes(charset)));
			response.headers().set(HttpHeaderNames.CONTENT_TYPE, message.getResType()+";charset=" + charset);
			response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());

			// 强制keep-alive
			response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
			response.headers().set("Keep-Alive", "timeout=" + timeout);

			out.add(response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}