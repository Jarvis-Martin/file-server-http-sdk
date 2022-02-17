package com.qiyuesuotest.server.application;

import com.qiyuesuotest.server.domain.Repository.FileServerRepository;
import com.qiyuesuotest.server.domain.ResponseStatusEnum;
import com.qiyuesuotest.server.domain.entity.FileMeta;
import com.qiyuesuotest.server.domain.entity.UploadFile;
import constant.Constant;
import exception.BussnissException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import util.FileUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

/**
 * @author: Coder_Jarvis
 * @description:
 * @date: 2022-02-2022/2/16-10:11
 * @version: 1.0
 */

/**
 * 应用服务：暴露本应用程序提供了服务接口
 */
@Service
public class FileServerApplicationService {
    private static final Logger logger = LoggerFactory.getLogger(FileServerApplicationService.class);

    @Autowired
    private FileServerRepository fileServerRepository;

    /**
     * 文件上传服务
     * @param file
     * @return
     */
    @Transactional
    public String uploadFile(MultipartFile file) {
        String fileName = UUID.randomUUID().toString();
        long fileSize = file.getSize();
        String fileType = file.getContentType();
        String fileOriginalName = file.getOriginalFilename();
        long createTime = Instant.now().toEpochMilli();
        String fileDirectory = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String fileExtension = FileUtil.getExtension(fileOriginalName);
        // 目标文件
        File dest = new File(
                Constant.FILEROOTPATH + File.separator + fileDirectory + File.separator +
                        fileName + "." + fileExtension);
        // 若文件已存在则重试生成文件名
        while (dest.exists()) {
            fileName = UUID.randomUUID().toString();
            dest = new File(
                    Constant.FILEROOTPATH + File.separator + fileDirectory + File.separator +
                            fileName + "." + fileExtension);
        }
        // 创建父目录
        dest.getParentFile().mkdirs();
        // 创建文件
        try {
            dest.createNewFile();
        } catch (IOException e) {
            logger.error("文件上传失败", e);
            throw new BussnissException(ResponseStatusEnum.ERROR.getErrorCode(), ResponseStatusEnum.ERROR.getErrorMessage());
        }
        // 保存文件
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            logger.error("文件上传失败", e);
            throw new BussnissException(ResponseStatusEnum.ERROR.getErrorCode(), ResponseStatusEnum.ERROR.getErrorMessage());
        }
        // 构建 UploadFile 对象
        UploadFile uploadFile = UploadFile.builder()
                .fileName(fileName)
                .fileSize(fileSize)
                .fileType(fileType)
                .fileOriginalName(fileOriginalName)
                .createTime(createTime)
                .fileDirectory(fileDirectory)
                .build();
        fileServerRepository.insertOne(uploadFile);
        return fileName;
    }

    /**
     * 文件下载服务
     * @param response
     * @param fileName
     */
    @Transactional
    public void downLoadFile(HttpServletResponse response, String fileName) {
        UploadFile uploadFile = fileServerRepository.findOne(fileName);
        File file = uploadFile.file();
        // 文件不存在 或 文件目录 或 隐藏文件时，直接抛出错误由全局异常吹里类 Global ExceptionHandler 处理，响应给客户端 ResponseDTO
        if (!file.exists() || file.isDirectory() || file.isHidden()) {
            throw new BussnissException(
                    ResponseStatusEnum.ERROR.getErrorCode(),
                    ResponseStatusEnum.ERROR.getErrorMessage());
        }
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + uploadFile.fileName() + "." + FileUtil.getExtension(uploadFile.fileOriginalName()));

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
            byte[] buff = new byte[1024];
            OutputStream os = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            logger.error("文件下载失败", e);
            throw new BussnissException(ResponseStatusEnum.ERROR.getErrorCode(), ResponseStatusEnum.ERROR.getErrorMessage());
        }
    }

    /**
     * 获取文件员信息服务
     * @param fileName
     * @return
     */
    public FileMeta fileMeta(String fileName) {
        // 从 db 中查找到
        UploadFile file = fileServerRepository.findOne(fileName);
        return FileMeta.builder()
                .fileName(file.fileName())
                .fileSize(file.fileSize())
                .fileType(file.fileType())
                .fileOriginalName(file.fileOriginalName())
                .createTime(file.createTime())
                .fileDirectory(file.fileDirectory())
                .build();
    }

}
