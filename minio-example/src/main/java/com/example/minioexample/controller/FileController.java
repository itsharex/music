package com.example.minioexample.controller;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @Author lyj
 * @since 2022/7/15
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @Resource
    private MinioClient minioClient;

    //Mioio配置
    private String endpoint = "http://127.0.0.1:9000";
    private String bucket = "test";

    /**
     * 上传文件
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/upload")
    public String upload(@RequestParam("file")MultipartFile file) throws Exception{
        //上传
        //文件名
        String path = UUID.randomUUID().toString();
        minioClient.putObject(PutObjectArgs.builder()
                //存储桶
                .bucket(bucket)
                //文件名
                .object(path)
                //文件内容
                .stream(file.getInputStream(),file.getSize(),-1)
                //文件类型
                .contentType(file.getContentType())
                .build());
        return String.format("%s/%s/%s",endpoint,bucket,path);
    }

    @DeleteMapping("/delete")
    public void deleteFile(@RequestParam("path")String path) throws Exception{
        minioClient.removeObject(RemoveObjectArgs.builder()
                //存储桶
                .bucket(bucket)
                //文件名
                .object(path)
                .build());
    }

}
