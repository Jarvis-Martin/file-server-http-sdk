package com.qiyuesuotest.server.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: Coder_Jarvis
 * @description:
 * @date: 2022-02-2022/2/16-11:14
 * @version: 1.0
 */
@Builder
public class FileMeta implements Serializable {
    /**
     * 上传后文件新名
     */
    private String fileName;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件原名称
     */
    private String fileOriginalName;

    /**
     * 文件创建时间，13位时间戳
     */
    private Long createTime;

    /**
     * 文件存放目录
     */
    private String fileDirectory;

    public String fileName() {
        return fileName;
    }

    public Long fileSize() {
        return fileSize;
    }

    public String fileType() {
        return fileType;
    }

    public String fileOriginalName() {
        return fileOriginalName;
    }

    public Long createTime() {
        return createTime;
    }

    public String fileDirectory() {
        return fileDirectory;
    }

    public void fileName(String fileName) {
        this.fileName = fileName;
    }

    public void fileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public void fileType(String fileType) {
        this.fileType = fileType;
    }

    public void fileOriginalName(String fileOriginalName) {
        this.fileOriginalName = fileOriginalName;
    }

    public void createTime(Long createTime) {
        this.createTime = createTime;
    }

    public void fileDirectory(String fileDirectory) {
        this.fileDirectory = fileDirectory;
    }
}
