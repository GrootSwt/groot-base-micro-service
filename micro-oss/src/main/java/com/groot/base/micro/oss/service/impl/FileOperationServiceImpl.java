package com.groot.base.micro.oss.service.impl;

import cn.hutool.core.io.FileTypeUtil;
import com.groot.base.micro.oss.repository.FileInfoRepository;
import com.groot.base.web.exception.BusinessRuntimeException;
import com.groot.base.micro.oss.model.FileInfo;
import com.groot.base.micro.oss.service.FileOperationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FileOperationServiceImpl implements FileOperationService {

    @Value(value = "${groot.file.root-path}")
    private String fileRootPath;
    @Resource
    private MultipartResolver multipartResolver;
    @Resource
    private FileInfoRepository fileInfoRepository;

    @Override
    public List<FileInfo> upload(HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest;
        if (multipartResolver.isMultipart(request)) {
            multipartRequest = multipartResolver.resolveMultipart(request);
        } else {
            multipartRequest = (MultipartHttpServletRequest) request;
        }
        String filesId = request.getParameter("filesId");
        List<FileInfo> fileInfoList = new ArrayList<>();
        MultiValueMap<String, MultipartFile> multiFileMap = multipartRequest.getMultiFileMap();
        multiFileMap.forEach((name, fileList) -> {
            fileList.forEach(file -> fileInfoList.add(saveFile(file, filesId)));
        });
        return fileInfoRepository.saveAll(fileInfoList);
    }

    @Override
    public void download(String id, HttpServletResponse response) throws IOException {
        FileInfo fileInfo = fileInfoRepository.findFirstById(id);
        if (fileInfo == null) {
            throw new BusinessRuntimeException("??????????????????");
        }
        String filePath = fileRootPath
                + "/"
                + fileInfo.getFilePath().substring(0, fileInfo.getFilePath().indexOf("_"))
                + "/"
                + fileInfo.getFilePath();
        File file = new File(filePath);
        if (!file.exists()) {
            throw new BusinessRuntimeException("??????????????????");
        }
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileInfo.getFileName(), "UTF-8"));
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(file);
            outputStream = response.getOutputStream();
            int len = 0;
            byte[] buffer = new byte[102400];
            while ((len = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert outputStream != null;
            outputStream.close();
            inputStream.close();
        }
    }

    @Override
    public FileInfo getFileInfoById(String id) {
        FileInfo fileInfo = fileInfoRepository.findFirstById(id);
        if (fileInfo == null) {
            throw new BusinessRuntimeException("??????????????????");
        }
        return fileInfo;
    }

    @Override
    public List<FileInfo> listFileInfoByIdArr(String[] idArr) {
        return fileInfoRepository.findAllById(Arrays.asList(idArr));
    }

    @Override
    public void deleteFileById(String id) {
        FileInfo fileInfo = fileInfoRepository.findFirstById(id);
        if (fileInfo == null) {
            throw new BusinessRuntimeException("?????????????????????????????????");
        }
        deleteFileAndInfo(fileInfo);
    }

    @Override
    public void deleteFileListByIdArr(String[] idArr) {
        List<FileInfo> fileInfoList = fileInfoRepository.findAllById(Arrays.asList(idArr));
        fileInfoList.forEach(this::deleteFileAndInfo);
    }

    @Override
    public List<FileInfo> getFileListByFilesId(String filesId) {
        return fileInfoRepository.findAllByFilesId(filesId);
    }

    @Override
    public List<String> getFileIdListByFilesId(String filesId) {
        return fileInfoRepository.getFileIdListByFilesId(filesId);
    }

    @Override
    public void deleteFilesByFilesId(String filesId) {
        fileInfoRepository.deleteAllByFilesId(filesId);
    }

    private FileInfo saveFile(MultipartFile file,String filesId) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFilesId(filesId);
        try {
            // ?????????
            String fileName = file.getOriginalFilename();
            fileInfo.setFileName(fileName);
            // ????????????
            fileInfo.setFileSize(file.getSize());
            // ????????????
            InputStream inputStream = file.getInputStream();
            fileInfo.setFileType(FileTypeUtil.getType(inputStream));
            inputStream.close();

            // ????????????
            assert fileName != null;
            String saveFileName = getSaveFileName(fileName);
            fileInfo.setFilePath(saveFileName);

            // ????????????????????????
            File outFile = createNewFile(saveFileName);
            // ????????????
            file.transferTo(outFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileInfo;
    }

    /**
     * ??????????????????????????????
     *
     * @param fileName ????????????
     * @return ?????????????????????
     */
    private String getSaveFileName(String fileName) {
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
        return "C"
                + yyyyMMdd.format(new Date())
                + "_"
                + UUID.randomUUID().toString().replaceAll("-", "")
                + fileName.substring(fileName.lastIndexOf("."));

    }

    /**
     * ??????????????????????????????????????????
     *
     * @param saveFileName ????????????????????????
     * @return ??????
     */
    private File createNewFile(String saveFileName) {
        File file = new File(fileRootPath, saveFileName.substring(0, saveFileName.indexOf("_")));
        if (!file.exists()) {
            file.mkdirs();
        }
        return new File(file, saveFileName);
    }

    /**
     * ???????????????????????????
     *
     * @param fileInfo ????????????
     */
    private void deleteFileAndInfo(FileInfo fileInfo) {
        String filePath = fileInfo.getFilePath().substring(0, fileInfo.getFilePath().indexOf("_"))
                + "/"
                + fileInfo.getFilePath();
        // ????????????
        File file = new File(fileRootPath, filePath);
        file.delete();
        File rootFile = new File(fileRootPath,fileInfo.getFilePath().substring(0, fileInfo.getFilePath().indexOf("_")));
        rootFile.delete();
        // ??????????????????
        fileInfoRepository.delete(fileInfo);
    }
}
