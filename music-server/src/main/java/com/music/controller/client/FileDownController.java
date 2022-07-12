package com.music.controller.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @author lyj
 * @data 2022/6/27 17:06
 */
@Controller
public class FileDownController {

    @Value("${filePath}")
    private String filePath;

    @GetMapping("/download")
    public void fileDownLoad(@RequestParam("file") String fileName, HttpServletRequest request, HttpServletResponse response){
        OutputStream outputStream = null;
        InputStream inputStream = null;
        BufferedInputStream bufferedInputStream = null;
        byte[] bytes = new byte[1024];
        File file = new File(filePath + "/" + fileName);
        try {
            //配置跨域
            response.reset();
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "*");
            response.addHeader("Access-Control-Allow-Headers", "*");
            // 以流的形式返回文件
            response.setContentType("application/octet-stream");
            response.setHeader("content-type", "application/octet-stream");
            response.setHeader("Content-Disposition","attachment;filename=" +  URLEncoder.encode(fileName, "UTF-8"));
            inputStream = new FileInputStream(file);
            bufferedInputStream = new BufferedInputStream(inputStream);
            outputStream = response.getOutputStream();
            int i = bufferedInputStream.read(bytes);
            while (i != -1){
                outputStream.write(bytes,0,i);
                i = bufferedInputStream.read(bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (bufferedInputStream != null){
                    bufferedInputStream.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}
