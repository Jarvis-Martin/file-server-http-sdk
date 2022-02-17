package com.qiyuesuotest.server.interfaces.facade.assembler;

import com.qiyuesuotest.server.domain.entity.FileMeta;
import com.qiyuesuotest.server.domain.entity.UploadFile;
import com.qiyuesuotest.server.interfaces.facade.restdto.FileMetaDTO;
import org.springframework.stereotype.Component;

/**
 * @author: Coder_Jarvis
 * @description:
 * @date: 2022-02-2022/2/16-11:16
 * @version: 1.0
 */

/**
 * DTO(data transport object) 和 DO(domain object) 转换器类
 */
@Component
public class UploadFileAssembler {
    public FileMetaDTO transform2FileMetaDTO(FileMeta meta) {
        FileMetaDTO metaDTO = new FileMetaDTO();
        metaDTO.setFileName(meta.fileName());
        metaDTO.setFileSize(meta.fileSize());
        metaDTO.setFileType(meta.fileType());
        metaDTO.setFileOriginalName(meta.fileOriginalName());
        metaDTO.setCreateTime(meta.createTime());
        metaDTO.setFileDirectory(meta.fileDirectory());
        return metaDTO;
    }
}
