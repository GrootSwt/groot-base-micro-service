package com.micro.oss.controller;

import com.micro.base.web.bean.ResultData;
import com.micro.oss.convertor.FileInfoConvertor;
import com.micro.base.web.exception.BusinessRuntimeException;
import com.micro.oss.model.FileInfo;
import com.micro.oss.service.FileOperationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Api(tags = "文件操作")
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
    @ApiOperation(value = "文件上传")
    @PostMapping(value = "upload")
    public ResultData upload(HttpServletRequest request) {
        List<FileInfo> fileInfoList = fileOperationService.upload(request);
        return ResultData.success("保存文件成功！", fileInfoConvertor.toListDTO(fileInfoList));
    }

    /**
     * 文件下载
     *
     * @param id       文件id
     * @param response 响应
     * @throws BusinessRuntimeException 文件不存在时
     * @throws IOException       IO异常
     */
    @ApiOperation(value = "文件下载")
    @GetMapping(value = "download/{id}")
    public void download(@PathVariable Long id, HttpServletResponse response) throws IOException {
        fileOperationService.download(id, response);
    }

    /**
     * 获取文件信息
     *
     * @param id 文件id
     * @return 文件信息
     * @throws BusinessRuntimeException 文件信息不存在
     */
    @ApiOperation(value = "获取文件信息")
    @GetMapping(value = "{id}/getFileInfoById")
    public ResultData getFileInfoById(@PathVariable Long id) {
        FileInfo fileInfo = fileOperationService.getFileInfoById(id);
        return ResultData.success("获取文件信息成功！", fileInfoConvertor.toDTO(fileInfo));
    }

    /**
     * 获取文件列表信息
     *
     * @param idArr 文件id列表
     * @return 文件列表信息
     */
    @ApiOperation(value = "获取文件列表信息")
    @GetMapping(value = "listFileInfoByIdArr")
    public ResultData listFileInfoByIdArr(@RequestParam Long[] idArr) {
        List<FileInfo> fileInfoList = fileOperationService.listFileInfoByIdArr(idArr);
        return ResultData.success("获取文件信息成功！", fileInfoConvertor.toListDTO(fileInfoList));
    }

    /**
     * 根据文件信息id删除文件和文件信息
     *
     * @param id 文件信息id
     * @return 文件是否删除成功
     * @throws BusinessRuntimeException 文件不存在异常
     */
    @ApiOperation(value = "根据文件信息id删除文件和文件信息")
    @DeleteMapping(value = "{id}/deleteFileById")
    public ResultData deleteFileById(@PathVariable Long id) {
        fileOperationService.deleteFileById(id);
        return ResultData.success("文件删除成功！");
    }

    /**
     * 根据文件信息id列表批量删除文件和文件信息
     *
     * @param idArr 文件信息id列表
     * @return 是否删除成功
     */
    @ApiOperation(value = "根据文件信息id列表批量删除文件和文件信息")
    @DeleteMapping(value = "deleteFileListByIdArr")
    public ResultData deleteFileListByIdArr(@RequestParam Long[] idArr) {
        fileOperationService.deleteFileListByIdArr(idArr);
        return ResultData.success("文件删除成功！");
    }
}