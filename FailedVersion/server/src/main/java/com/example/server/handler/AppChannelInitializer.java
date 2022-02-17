package com.example.server.handler;

import com.example.commons.constant.PathConstant;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author: Coder_Jarvis
 * @description:
 * @date: 2022-02-2022/2/15-9:36
 * @version: 1.0
 */
public class AppChannelInitializer extends ChannelInitializer {

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline()
                .addLast("http-decoder", new HttpRequestDecoder())
                //.addLast("http-aggregator", new HttpObjectAggregator(65536))
                .addLast("http-encoder", new HttpResponseEncoder())
                //.addLast("http-chunked", new ChunkedWriteHandler())
                //.addLast("http-request-checker-handler", new HttpRequestCheckerHandler())
                .addLast("fileServerHandler", new FileHandler(PathConstant.FILEHANDLERPATH));
    }

}
