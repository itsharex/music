package com.music.controller.common;

import cn.hutool.core.util.IdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author lyj
 * @data 2022/6/23 16:07
 */
@RestController
public class FileUploadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadController.class);

    @Value("${filePath}")
    private String filePath;

    /**
     * TODO
     * 单文件上传
     * @Param [file]
     * @return java.lang.String
     */
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        if(file.isEmpty()){
            throw new RuntimeException("上传文件不能为空");
        }
        LOGGER.info("[文件类型] - [{}]", file.getContentType());
        LOGGER.info("[文件名称] - [{}]", file.getOriginalFilename());
        LOGGER.info("[文件大小] - [{}]", file.getSize());
        //文件名
        String fileName = file.getOriginalFilename();
        //后缀名
        //如何fileName 不等于空，则返回true继续执行之后的代码，返回false则抛出异常
        assert fileName != null;
        String suffixName = fileName.substring(fileName.indexOf("."));
        //文件存储名称
        fileName = IdUtil.simpleUUID() + suffixName;
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
        }catch (IOException e){
            e.printStackTrace();
        }
        return "/file/" + fileName;
    }
}
