package com.example.server.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.commons.response.ResponseDTO;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

/**
 * @author: Coder_Jarvis
 * @description:
 * @date: 2022-02-2022/2/15-15:04
 * @version: 1.0
 */
public class ResponseUtil {
    /**
     * 响应客户端
     * @param ctx
     * @param httpStatusCode http 响应状态码
     * @param responseDTO 响应给客户端的自定义响应信息
     */
    public static void responseToClinet(ChannelHandlerContext ctx, HttpResponseStatus httpStatusCode, ResponseDTO responseDTO) {
        FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                httpStatusCode,
                Unpooled.copiedBuffer(JSON.toJSONString(responseDTO), CharsetUtil.UTF_8));
        String string = JSON.toJSONString(responseDTO);
        ctx.writeAndFlush(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
    }
}
