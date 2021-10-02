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

    /**
     * 文件上传
     *
     * @param request 请求
     * @return 文件信息
     */
    @PostMapping(value = "upload")
    public ResultUtil upload(HttpServletRequest request) {
        List<FileInfo> fileInfoList = fileOperationService.upload(request);
        return ResultUtil.success("保存文件成功！", fileInfoConvertor.toListDTO(fileInfoList));
    }

    /**
     * 文件下载
     *
     * @param id       文件id
     * @param response 响应
     * @throws BusinessException 文件不存在时
     * @throws IOException       IO异常
     */
    @GetMapping(value = "download/{id}")
    public void download(@PathVariable Long id, HttpServletResponse response) throws BusinessException, IOException {
        fileOperationService.download(id, response);
    }

    /**
     * 获取文件信息
     *
     * @param id 文件id
     * @return 文件信息
     * @throws BusinessException 文件信息不存在
     */
    @GetMapping(value = "{id}/getFileInfoById")
    public ResultUtil getFileInfoById(@PathVariable Long id) throws BusinessException {
        FileInfo fileInfo = fileOperationService.getFileInfoById(id);
        return ResultUtil.success("获取文件信息成功！", fileInfoConvertor.toDTO(fileInfo));
    }

    /**
     * 获取文件列表信息
     *
     * @param idArr 文件id列表
     * @return 文件列表信息
     */
    @GetMapping(value = "listFileInfoByIdArr")
    public ResultUtil listFileInfoByIdArr(@RequestParam Long[] idArr) {
        List<FileInfo> fileInfoList = fileOperationService.listFileInfoByIdArr(idArr);
        return ResultUtil.success("获取文件信息成功！", fileInfoConvertor.toListDTO(fileInfoList));
    }

    /**
     * 根据文件信息id删除文件和文件信息
     *
     * @param id 文件信息id
     * @return 文件是否删除成功
     * @throws BusinessException 文件不存在异常
     */
    @DeleteMapping(value = "{id}/deleteFileById")
    public ResultUtil deleteFileById(@PathVariable Long id) throws BusinessException {
        fileOperationService.deleteFileById(id);
        return ResultUtil.success("文件删除成功！");
    }

    /**
     * 根据文件信息id列表批量删除文件和文件信息
     *
     * @param idArr 文件信息id列表
     * @return 是否删除成功
     */
    @DeleteMapping(value = "deleteFileListByIdArr")
    public ResultUtil deleteFileListByIdArr(@RequestParam Long[] idArr) {
        fileOperationService.deleteFileListByIdArr(idArr);
        return ResultUtil.success("文件删除成功！");
    }
}
