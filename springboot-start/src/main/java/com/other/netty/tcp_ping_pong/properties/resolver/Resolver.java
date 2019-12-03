package com.other.netty.tcp_ping_pong.properties.resolver;

import com.other.netty.tcp_ping_pong.properties.response.Message;

public interface Resolver {

	boolean support(Message message);

	Message resolve(Message message);

}
