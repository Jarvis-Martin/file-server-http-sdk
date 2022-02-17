package com.qiyuesuotest.server.domain.entity;

import java.io.File;
import java.io.Serializable;
import java.util.Optional;

import constant.Constant;
import lombok.Builder;
import util.FileUtil;

/**
 * file
 *
 * @author
 */
@Builder
public class UploadFile implements Serializable {
    /**
     * 文件编号
     */
    private Integer id;

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

    private File file;

    public Integer id() {
        return id;
    }

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

    public File file() {
        /**
         * 如果 this.file 为 null，则从磁盘加载文件
         */
        return Optional.ofNullable(this.file)
                .orElseGet(
                        () -> {
                            this.file(new File(Constant.FILEROOTPATH + File.separator + this.fileDirectory() + File.separator +
                                    fileName + "." + FileUtil.getExtension(this.fileOriginalName())));
                            return this.file();
                        }
                );
    }

    // ---------------
    public void id(Integer id) {
        this.id = id;
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

    public void fileOriginalName(String fileOrignName) {
        this.fileOriginalName = fileOrignName;
    }

    public void createTime(Long createTime) {
        this.createTime = createTime;
    }

    public void fileDirectory(String fileDirectory) {
        this.fileDirectory = fileDirectory;
    }

    public void file(File file) {
        this.file = file;
    }

    private static final long serialVersionUID = 1L;
}