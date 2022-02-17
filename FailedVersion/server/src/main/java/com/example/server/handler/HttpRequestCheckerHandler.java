package com.example.server.handler;

import com.example.commons.response.ResponseDTO;
import com.example.server.ServerApplication;
import com.example.server.util.ResponseUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * @author: Coder_Jarvis
 * @description:
 * @date: 2022-02-2022/2/15-14:48
 * @version: 1.0
 */
/**
 * 该类用于检查收到的请求是否为 http 请求
 */
public class HttpRequestCheckerHandler extends ChannelInboundHandlerAdapter {
    private final Logger logger = LoggerFactory.getLogger(HttpRequestCheckerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 若不是 http 请求, 或 请求解析出错，响应 BAD_REQUEST
        if (!(msg instanceof FullHttpRequest) || !((FullHttpRequest) msg).getDecoderResult().isSuccess()) {
            logger.info("接收到一个请求，但不是 http 请求");
            // 响应客户端 BAD_REQUEST
            ResponseDTO responseDTO = new ResponseDTO(HttpResponseStatus.BAD_REQUEST.code(),
                    "server端解析出错，不是 http 请求",
                    new HashMap<>(1));
            ResponseUtil.responseToClinet(ctx, HttpResponseStatus.BAD_REQUEST, responseDTO);
        }
        ctx.fireChannelRead(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.info(cause.getMessage());
        // 响应客户端 INTERNAL_SERVER_ERROR
        ResponseDTO responseDTO = new ResponseDTO(HttpResponseStatus.INTERNAL_SERVER_ERROR.code(),
                "服务器内部出现错误, 出错原因为:" + cause.getMessage(),
                new HashMap<>(1));
        ResponseUtil.responseToClinet(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR, responseDTO);
    }

}
