package com.wjh.netty.udp.service;

import com.wjh.netty.udp.handler.ServerHandlerExt;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class ServerExt implements Runnable {

    public int port;

    public ServerExt(int port){
        this.port = port;
    }

    @Override
    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new ServerHandlerExt());
            try {
                b.bind(port).sync().channel().closeFuture().await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(new ServerExt(6666)).start();
        //new Thread(new ServerExt(8888)).start();
    }

}