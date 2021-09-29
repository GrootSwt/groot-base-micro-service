package com.micro.swt.controller;

import com.micro.common.util.ResultUtil;
import com.micro.swt.convertor.FileInfoConvertor;
import com.micro.swt.exception.BusinessException;
import com.micro.swt.model.FileInfo;
import com.micro.swt.service.FileOperationService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "fileOperation")
public class FileOperationController {

    @Resource
    private FileOperationService fileOperationService;
    @Resource
    private FileInfoConvertor fileInfoConvertor;

    @PostMapping(value = "upload")
    public ResultUtil upload(HttpServletRequest request) {
        List<FileInfo> fileInfoList = fileOperationService.upload(request);
        return ResultUtil.success("保存文件成功！", fileInfoConvertor.toListDTO(fileInfoList));
    }

    @GetMapping(value = "{id}/download")
    public void download(@PathVariable Long id, HttpServletResponse response) throws BusinessException, IOException {
        fileOperationService.download(id, response);
    }
}
