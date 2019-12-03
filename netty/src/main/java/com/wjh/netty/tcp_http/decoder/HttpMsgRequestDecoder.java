package com.wjh.netty.tcp_http.decoder;
import java.nio.charset.Charset;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpObject;

public class HttpMsgRequestDecoder extends MessageToMessageDecoder<HttpObject>{
	private String charset;

	public HttpMsgRequestDecoder(String charset) {
		super();
		this.charset = charset;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, HttpObject in,
			List<Object> out) throws Exception {
		FullHttpRequest request = (FullHttpRequest) in;

		ByteBuf buf = request.content();
		String jsonStr = buf.toString(Charset.forName(charset));
		out.add(jsonStr);
	}
}
