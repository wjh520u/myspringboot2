package com.wjh.netty.tcp.handler;

import com.wjh.netty.tcp.properties.response.SmartCarProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
  
public class ServerHandler extends SimpleChannelInboundHandler<Object> {  
    // 用于获取客户端发送的信息  
    @Override  
    public void channelRead0(ChannelHandlerContext ctx, Object msg)  
          throws Exception  {  
        // 用于获取客户端发来的数据信息  
        SmartCarProtocol body = (SmartCarProtocol) msg;
        System.out.println("Server接受的客户端的信息 :" + body.toString());  
  
        // 会写数据给客户端  
        String str = "Hi I am Server ...";  
        SmartCarProtocol response = new SmartCarProtocol(str.getBytes().length,  
                str.getBytes());
        System.out.println(Thread.currentThread().getName());
        // 当服务端完成写操作后，关闭与客户端的连接  
        ctx.writeAndFlush(response);
        new Thread(new Runnable(){

            @Override
            public void run() {
                System.out.println("????????????????????????????");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                SmartCarProtocol response = new SmartCarProtocol("我来也".getBytes().length,
                        "我来也".getBytes());
                System.out.println(Thread.currentThread().getName());
                ctx.writeAndFlush(response);
            }
        }).start();

        // .addListener(ChannelFutureListener.CLOSE);  
  
        // 当有写操作时，不需要手动释放msg的引用  
        // 当只有读操作时，才需要手动释放msg的引用  
    }  
  
    @Override  
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)  
            throws Exception {  
        // cause.printStackTrace();  
        ctx.close();  
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelRegistered");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelUnregistered");
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive");
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead");
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelReadComplete");
        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("userEventTriggered");
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelWritabilityChanged");
        super.channelWritabilityChanged(ctx);
    }


}