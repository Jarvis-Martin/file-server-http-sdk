package com.qiyuesuotest.server.infrastructure.repository;

import com.qiyuesuotest.server.domain.Repository.FileServerRepository;
import com.qiyuesuotest.server.domain.entity.UploadFile;
import com.qiyuesuotest.server.infrastructure.dao.FileMapper;
import com.qiyuesuotest.server.infrastructure.po.UploadFilePO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author: Coder_Jarvis
 * @description:
 * @date: 2022-02-2022/2/16-10:30
 * @version: 1.0
 */

@Repository
public class FileServerRepositoryImpl implements FileServerRepository {

    @Autowired
    private FileMapper fileMapper;

    @Override
    public UploadFile findOne(String fileName) {
        // 查询 po(persistent object) 并构建 do (domain object)
        UploadFilePO filePO = fileMapper.findOne(fileName);
        return UploadFile.builder()
                .id(filePO.getId())
                .fileName(filePO.getFileName())
                .fileSize(filePO.getFileSize())
                .fileType(filePO.getFileType())
                .fileOriginalName(filePO.getFileOriginalName())
                .createTime(filePO.getCreateTime())
                .fileDirectory(filePO.getFileDirectory())
                .build();
    }

    @Override
    public void insertOne(UploadFile file) {
        fileMapper.insertOne(file);
    }
}
