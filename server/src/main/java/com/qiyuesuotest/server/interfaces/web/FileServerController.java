package com.qiyuesuotest.server.interfaces.web;

import com.qiyuesuotest.server.domain.ResponseStatusEnum;
import com.qiyuesuotest.server.domain.entity.FileMeta;
import com.qiyuesuotest.server.interfaces.facade.assembler.UploadFileAssembler;
import com.qiyuesuotest.server.interfaces.facade.restdto.FileMetaDTO;
import com.qiyuesuotest.server.application.FileServerApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import response.ResponseDTO;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * @author: Coder_Jarvis
 * @description:
 * @date: 2022-02-2022/2/16-9:35
 * @version: 1.0
 */
@Controller
@RequestMapping("file")
public class FileServerController {
    @Autowired
    private FileServerApplicationService fileServerApplicationService;
    @Autowired
    private UploadFileAssembler fileAssembler;

    /**
     * 文件上传接口
     * @param file
     * @return
     */
    @ResponseBody
    @PostMapping("upload")
    public ResponseDTO uploadFile(MultipartFile file) {
        if (null == file) {
            return response(ResponseStatusEnum.ERROR, "{file == null}");
        }
        String fileName = fileServerApplicationService.uploadFile(file);
        HashMap<String, String> data = new HashMap<>(1);
        data.put("fileName", fileName);
        return response(ResponseStatusEnum.SUCCESS, data);
    }

    /**
     * 文件下载接口
     * @param response
     * @param fileName
     */
    @GetMapping("download/{fileName}")
    public void downLoadFile(HttpServletResponse response, @PathVariable("fileName") String fileName) {
        fileServerApplicationService.downLoadFile(response, fileName);
    }

    /**
     * 文件元信息获取接口
     * @param fileName
     * @return
     */
    @ResponseBody
    @GetMapping("meta/{fileName}")
    public ResponseDTO fileMeta(@PathVariable("fileName") String fileName) {
        FileMeta fileMeta = fileServerApplicationService.fileMeta(fileName);
        FileMetaDTO metaDTO = fileAssembler.transform2FileMetaDTO(fileMeta);
        return response(ResponseStatusEnum.SUCCESS, metaDTO);
    }

    /**
     * 通用的放回 ResponseDTO
     *
     * @param status
     * @param data
     * @return
     */
    private ResponseDTO response(ResponseStatusEnum status, Object data) {
        return new ResponseDTO(
                status.getErrorCode(),
                status.getErrorMessage(),
                data
        );
    }
}
