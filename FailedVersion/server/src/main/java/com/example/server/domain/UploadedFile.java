package com.example.server.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * file
 * @author 
 */
@Data
public class UploadedFile implements Serializable {
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
    private Integer fileSize;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件原名称
     */
    private String fileOrignName;

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