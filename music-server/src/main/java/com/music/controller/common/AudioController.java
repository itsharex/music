package com.music.controller.common;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.IoUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author lyj
 * @data 2022/6/29 16:25
 */
@RestController
public class AudioController {
    @Value("${filePath}")
    private String filePath;

    @GetMapping("/audio/file/{fileName:.+}")
    public void audioPreview(@PathVariable String fileName, HttpServletRequest request, HttpServletResponse response){
        //真实路径
        String realPath = filePath + fileName;
        InputStream is = null;
        OutputStream os = null;
        try {
            File file = new File(realPath);
            //解决音频播放不能拖拽进度条
            response.setContentLength(Convert.toInt(file.length()));
            response.setHeader("Accept-Ranges","bytes");
            response.setContentType("audio/mp3");
            is = new FileInputStream(realPath);
            os = response.getOutputStream();
            IoUtil.copy(is,os);
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
