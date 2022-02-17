package com.qiyuesuotest.server.interfaces.facade.restdto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: Coder_Jarvis
 * @description:
 * @date: 2022-02-2022/2/16-11:14
 * @version: 1.0
 */

/**
 * 根据前端需要定义的数据传输对象，对应 文件元信息获取接口
 */
@Data
public class FileMetaDTO implements Serializable {
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
}
