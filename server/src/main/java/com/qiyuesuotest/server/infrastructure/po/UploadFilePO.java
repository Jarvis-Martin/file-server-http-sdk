package com.qiyuesuotest.server.infrastructure.po;

import lombok.Data;

import java.io.Serializable;

/**
 * file
 * @author 
 */

/**
 * 持久化对象 和 数据表字段对应
 */
@Data
public class UploadFilePO implements Serializable {
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

    private static final long serialVersionUID = 1L;
}