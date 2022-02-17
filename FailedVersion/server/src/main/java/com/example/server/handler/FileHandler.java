package com.example.server.handler;

import com.alibaba.fastjson.JSONObject;
import com.example.commons.constant.Constant;
import com.example.commons.constant.PathConstant;
import com.example.commons.exception.NotFoundException;
import com.example.commons.response.ResponseDTO;
import com.example.server.util.ResponseUtil;
import com.sun.istack.internal.NotNull;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author: Coder_Jarvis
 * @description:
 * @date: 2022-02-2022/2/15-14:19
 * @version: 1.0
 */
public class FileHandler extends SimpleChannelInboundHandler<HttpObject> {
    private final Logger logger = LoggerFactory.getLogger(FileHandler.class);
    private HttpRequest request;

    private HttpData partialContent;

    private static final HttpDataFactory factory =
            new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE); // Disk if size exceed

    private HttpPostRequestDecoder decoder;

    static {
        DiskFileUpload.deleteOnExitTemporaryFile = true; // should delete file
        // on exit (in normal
        // exit)
        DiskFileUpload.baseDirectory = null; // system temp directory
        DiskAttribute.deleteOnExitTemporaryFile = true; // should delete file on
        // exit (in normal exit)
        DiskAttribute.baseDirectory = null; // system temp directory
    }

    /**
     * 文件服务器对应 url 路径
     */
    private String path;

    public FileHandler(@NotNull String fileHandlerPath) {
        this.path = fileHandlerPath;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws NotFoundException, FileNotFoundException, URISyntaxException {
        HttpContent chunk = (HttpContent) msg;
        decoder.offer(chunk);
        readHttpDataChunkByChunk();
        // example of reading only if at the end
        if (chunk instanceof LastHttpContent) {
            reset();
        }

        HttpRequest request = this.request = (HttpRequest) msg;
        // 如果请求路径不是 PathConstant.FILEHANDLERPATH = "file"
        if (!request.getUri().startsWith(PathConstant.FILEHANDLERPATH)) {
            ctx.fireChannelRead(msg);
        }
        String subURI = request.getUri().substring(PathConstant.FILEHANDLERPATH.length());
        HttpMethod requestMethod = request.getMethod();
        // GET 请求
        if (HttpMethod.GET.equals(requestMethod)) {
            switch (subURI) {
                // 文件下载
                case PathConstant.DOWNLOADFILEPATH: {
                    //handleDownloadFile();
                    break;
                }
                // 获取文件元信息
                case PathConstant.METAFILEPATH: {
                    //handleGetMetaInfo();
                    break;
                }
                // 资源未找到
                default: {
                    throw new NotFoundException();
                }
            }
        }
        // POST 请求
        if (HttpMethod.POST.equals(requestMethod)) {
            switch (subURI) {
                // 上传文件
                case PathConstant.UPLOADFILEPATH: {
                    HttpVersion httpVersion;
                    handleUploadFile(ctx, request);
                    break;
                }
                // 资源未找到
                default: {
                    throw new NotFoundException();
                }
            }
        }
    }

    private void handleUploadFile(ChannelHandlerContext ctx, Object msg) throws FileNotFoundException, URISyntaxException {
        decoder = new HttpPostRequestDecoder(factory, request);
        if (decoder != null) {
            if (msg instanceof HttpContent) {
                // New chunk is received
                HttpContent chunk = (HttpContent) msg;
                decoder.offer(chunk);
                readHttpDataChunkByChunk();
                // example of reading only if at the end
                if (chunk instanceof LastHttpContent) {
                    reset();
                }
            }
        }

        //UUID fileName = UUID.randomUUID();
        //ByteBuf content = request.content();
        //File file = new File(Constant.FILEROOTPATH + fileName);
        //// 若文件已存在则重试生成文件名
        //while (file.exists()) {
        //    fileName = UUID.randomUUID();
        //    file = new File(Constant.FILEROOTPATH + fileName);
        //}

    }

    private void reset() {
        request = null;
        // destroy the decoder to release all resources
        decoder.destroy();
        decoder = null;
    }

    private void writeHttpData(InterfaceHttpData data) {
        if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute) {
            Attribute attribute = (Attribute) data;
            String value;
            try {
                value = attribute.getValue();
            } catch (IOException e1) {
                // Error while reading data from File, only print name and error
                e1.printStackTrace();
                return;
            }
        } else {
            if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.FileUpload) {
                FileUpload fileUpload = (FileUpload) data;
                if (fileUpload.isCompleted()) {
                    logger.info("文件上传完成");
                    // fileUpload.isInMemory();// tells if the file is in Memory
                    // or on File
                    // fileUpload.renameTo(dest); // enable to move into another
                    // File dest
                    // decoder.removeFileUploadFromClean(fileUpload); //remove
                    // the File of to delete file
                } else {
                    logger.info("文件上传 ing");
                }
            }
        }
    }

    private void readHttpDataChunkByChunk() {
        try {
            while (decoder.hasNext()) {
                InterfaceHttpData data = decoder.next();
                if (data != null) {
                    // check if current HttpData is a FileUpload and previously set as partial
                    if (partialContent == data) {
                        logger.info(" 100% (FinalSize: " + partialContent.length() + ")");
                        partialContent = null;
                    }
                    // new value
                    writeHttpData(data);
                }
            }
            // Check partial decoding for a FileUpload
            InterfaceHttpData data = decoder.currentPartialHttpData();
            if (data != null) {
                StringBuilder builder = new StringBuilder();
                if (partialContent == null) {
                    partialContent = (HttpData) data;
                    if (partialContent instanceof FileUpload) {
                        builder.append("Start FileUpload: ")
                                .append(((FileUpload) partialContent).getFilename()).append(" ");
                    } else {
                        builder.append("Start Attribute: ")
                                .append(partialContent.getName()).append(" ");
                    }
                    builder.append("(DefinedSize: ").append(partialContent.definedLength()).append(")");
                }
                if (partialContent.definedLength() > 0) {
                    builder.append(" ").append(partialContent.length() * 100 / partialContent.definedLength())
                            .append("% ");
                    logger.info(builder.toString());
                } else {
                    builder.append(" ").append(partialContent.length()).append(" ");
                    logger.info(builder.toString());
                }
            }
        } catch (HttpPostRequestDecoder.EndOfDataDecoderException e1) {
            // end
            logger.info("文件上传 readHttpDataChunkByChunk");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.info(cause.getMessage());
        // 响应客户端 INTERNAL_SERVER_ERROR
        ResponseDTO responseDTO = new ResponseDTO(HttpResponseStatus.INTERNAL_SERVER_ERROR.code(),
                cause.getMessage(),
                new HashMap<>(1));
        ResponseUtil.responseToClinet(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR, responseDTO);
    }
}
