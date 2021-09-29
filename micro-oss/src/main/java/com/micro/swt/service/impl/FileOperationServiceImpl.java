package com.micro.swt.service.impl;

import cn.hutool.core.io.FileTypeUtil;
import com.micro.swt.exception.BusinessException;
import com.micro.swt.model.FileInfo;
import com.micro.swt.repository.FileInfoRepository;
import com.micro.swt.service.FileOperationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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

    @Value(value = "${file.fileRootPath}")
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
        List<FileInfo> fileInfoList = new ArrayList<>();
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        fileMap.values().forEach(file -> fileInfoList.add(saveFile(file)));
        return fileInfoRepository.saveAll(fileInfoList);
    }

    @Override
    public void download(Long id, HttpServletResponse response) throws BusinessException, IOException {
        FileInfo fileInfo = fileInfoRepository.findFirstById(id);
        if (fileInfo == null) {
            throw new BusinessException("文件不存在！");
        }
        String filePath = fileRootPath
                + "/"
                + fileInfo.getFilePath().substring(0, fileInfo.getFilePath().indexOf("_"))
                + "/"
                + fileInfo.getFilePath();
        File file = new File(filePath);
        if (!file.exists()) {
            throw new BusinessException("文件不存在！");
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

    private FileInfo saveFile(MultipartFile file) {
        FileInfo fileInfo = new FileInfo();
        try {
            // 文件名
            String fileName = file.getOriginalFilename();
            fileInfo.setFileName(fileName);
            // 文件大小
            fileInfo.setFileSize(file.getSize());
            // 文件类型
            InputStream inputStream = file.getInputStream();
            fileInfo.setFileType(FileTypeUtil.getType(inputStream));
            inputStream.close();

            // 文件路径
            String saveFileName = getSaveFileName(fileName);
            fileInfo.setFilePath(saveFileName);

            // 创建本地保存文件
            File outFile = createNewFile(saveFileName);
            // 保存文件
            file.transferTo(outFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileInfo;
    }

    /**
     * 生成本地保存的文件名
     * @param fileName 文件本名
     * @return 本地保存文件名
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
     * 根据本地保存的文件名创建文件
     * @param saveFileName 本地保存的文件名
     * @return 文件
     */
    private File createNewFile(String saveFileName) {
        File file = new File(fileRootPath, saveFileName.substring(0, saveFileName.indexOf("_")));
        if (!file.exists()) {
            file.mkdirs();
        }
        return new File(file, saveFileName);
    }
}
