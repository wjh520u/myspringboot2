package com.wjh.netty.tcp_ping_pong.encoder;

import java.nio.charset.Charset;

import com.wjh.netty.tcp_ping_pong.properties.NConstants;
import com.wjh.netty.tcp_ping_pong.properties.SessionIdGenerator;
import com.wjh.netty.tcp_ping_pong.properties.enums.MessageTypeEnum;
import com.wjh.netty.tcp_ping_pong.properties.response.Message;
import org.springframework.util.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageEncoder extends MessageToByteEncoder<Message> {

	  @Override
	  protected void encode(ChannelHandlerContext ctx, Message message, ByteBuf out) {
	    // 这里会判断消息类型是不是EMPTY类型，如果是EMPTY类型，则表示当前消息不需要写入到管道中
	    if (message.getMessageType() != MessageTypeEnum.EMPTY) {
	      out.writeInt(NConstants.MAGIC_NUMBER);	// 写入当前的魔数
	      out.writeByte(NConstants.MAIN_VERSION);	// 写入当前的主版本号
	      out.writeByte(NConstants.SUB_VERSION);	// 写入当前的次版本号
	      out.writeByte(NConstants.MODIFY_VERSION);	// 写入当前的修订版本号
	      if (!StringUtils.hasText(message.getSessionId())) {
	        // 生成一个sessionId，并将其写入到字节序列中
	        String sessionId = SessionIdGenerator.generate();
	        message.setSessionId(sessionId);
	        out.writeCharSequence(sessionId, Charset.defaultCharset());
	      }

	      out.writeByte(message.getMessageType().getType());	// 写入当前消息的类型
	      out.writeShort(message.getAttachments().size());	// 写入当前消息的附加参数数量
	      message.getAttachments().forEach((key, value) -> {
	        Charset charset = Charset.defaultCharset();
	        out.writeInt(key.length());	// 写入键的长度
	        out.writeCharSequence(key, charset);	// 写入键数据
	        out.writeInt(value.length());	// 希尔值的长度
	        out.writeCharSequence(value, charset);	// 写入值数据
	      });

	      if (null == message.getBody()) {
	        out.writeInt(0);	// 如果消息体为空，则写入0，表示消息体长度为0
	      } else {
	        out.writeInt(message.getBody().length());
	        out.writeCharSequence(message.getBody(), Charset.defaultCharset());
	      }
	    }
	  }
}