package com.example.server;

import com.example.commons.constant.Constant;
import com.example.server.handler.AppChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: Coder_Jarvis
 * @description:
 * @date: 2022-02-2022/2/15-9:28
 * @version: 1.0
 */
public class ServerApplication {
    private static final Logger logger = LoggerFactory.getLogger(ServerApplication.class);

    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new AppChannelInitializer());
            ChannelFuture future = b.bind(Constant.SERVERADDRESS, Constant.SERVERLISTENNINGPORT);
            logger.info("文件服务器启动成功，地址: http://" + Constant.SERVERADDRESS + ":" +  Constant.SERVERLISTENNINGPORT);
            future.channel()
                    .closeFuture()
                    .sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
