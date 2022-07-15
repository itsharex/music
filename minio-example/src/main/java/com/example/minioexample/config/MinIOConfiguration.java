package com.example.minioexample.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author lyj
 * @since 2022/7/15
 */
@Configuration
public class MinIOConfiguration {

    @Bean
    public MinioClient minioClient(){
        //Minio配置
        String endpoint = "http://127.0.0.1:9000";
        String accessKey = "minioadmin";
        String secrekey = "minioadmin";

        //创建Mioio客户端
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey,secrekey)
                .build();
    }
}
