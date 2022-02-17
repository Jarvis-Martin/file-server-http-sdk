package com.qiyuesuotest.server.infrastructure.dao;

import com.qiyuesuotest.server.domain.entity.UploadFile;
import com.qiyuesuotest.server.infrastructure.po.UploadFilePO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {
    /**
     * 向 db 中加入一条文件元信息 记录
     * @param file
     */
    void insertOne(UploadFile file);

    /**
     * 根据 文件名(UUID) 查找记录
     * @param fileName
     * @return
     */
    UploadFilePO findOne(String fileName);
}