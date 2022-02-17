package com.qiyuesuotest.server.domain.Repository;

import com.qiyuesuotest.server.domain.entity.UploadFile;
import com.qiyuesuotest.server.infrastructure.po.UploadFilePO;
import org.springframework.stereotype.Repository;

/**
 * @author: Coder_Jarvis
 * @description:
 * @date: 2022-02-2022/2/16-10:28
 * @version: 1.0
 */
public interface FileServerRepository {
    UploadFile findOne(String fileName);

    void insertOne(UploadFile file);
}
