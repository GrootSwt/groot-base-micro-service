package com.micro.oss.service.impl;

import cn.hutool.core.io.FileTypeUtil;
import com.micro.base.web.exception.BusinessRuntimeException;
import com.micro.oss.model.FileInfo;
import com.micro.oss.repository.FileInfoRepository;
import com.micro.oss.service.FileOperationService;
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

    @Value(value = "${micro.file.rootPath}")
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
        MultiValueMap<String, MultipartFile> multiFileMap = multipartRequest.getMultiFileMap();
        multiFileMap.forEach((name, fileList) -> {
            fileList.forEach(file -> fileInfoList.add(saveFile(file)));
        });
        return fileInfoRepository.saveAll(fileInfoList);
    }

    @Override
    public void download(Long id, HttpServletResponse response) throws IOException {
        FileInfo fileInfo = fileInfoRepository.findFirstById(id);
        if (fileInfo == null) {
            throw new BusinessRuntimeException("文件不存在！");
        }
        String filePath = fileRootPath
                + "/"
                + fileInfo.getFilePath().substring(0, fileInfo.getFilePath().indexOf("_"))
                + "/"
                + fileInfo.getFilePath();
        File file = new File(filePath);
        if (!file.exists()) {
            throw new BusinessRuntimeException("文件不存在！");
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
    public FileInfo getFileInfoById(Long id) {
        FileInfo fileInfo = fileInfoRepository.findFirstById(id);
        if (fileInfo == null) {
            throw new BusinessRuntimeException("文件不存在！");
        }
        return fileInfo;
    }

    @Override
    public List<FileInfo> listFileInfoByIdArr(Long[] idArr) {
        return fileInfoRepository.findAllById(Arrays.asList(idArr));
    }

    @Override
    public void deleteFileById(Long id) {
        FileInfo fileInfo = fileInfoRepository.findFirstById(id);
        if (fileInfo == null) {
            throw new BusinessRuntimeException("将要删除的文件不存在！");
        }
        deleteFileAndInfo(fileInfo);
    }

    @Override
    public void deleteFileListByIdArr(Long[] idArr) {
        List<FileInfo> fileInfoList = fileInfoRepository.findAllById(Arrays.asList(idArr));
        fileInfoList.forEach(this::deleteFileAndInfo);
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
            assert fileName != null;
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
     *
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
     *
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

    /**
     * 删除文件和文件信息
     *
     * @param fileInfo 文件信息
     */
    private void deleteFileAndInfo(FileInfo fileInfo) {
        String filePath = fileInfo.getFilePath().substring(0, fileInfo.getFilePath().indexOf("_"))
                + "/"
                + fileInfo.getFilePath();
        // 删除文件
        File file = new File(fileRootPath, filePath);
        file.delete();
        File rootFile = new File(fileRootPath,fileInfo.getFilePath().substring(0, fileInfo.getFilePath().indexOf("_")));
        rootFile.delete();
        // 删除文件信息
        fileInfoRepository.delete(fileInfo);
    }
}
