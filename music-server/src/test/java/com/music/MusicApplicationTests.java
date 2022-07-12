package com.music;
import cn.hutool.core.convert.Convert;
import cn.hutool.crypto.SecureUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class MusicApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void test1(){
        String email = "20000333@qq.com#0";
        Object obj = StringUtils.substringAfter(email,"#");
        email = Convert.toStr(StringUtils.substringBefore(email,"#"));
        System.out.println(obj.equals("0"));
        System.out.println(email);
    }


    @Test
    void test3(){
        System.out.println(System.getProperty("user.dir")); //uploadFile;
    }

    @Test
    void test4(){
        String password = "123";
        System.out.println(SecureUtil.md5(password));
    }

    @Test
    void test5(){
        String path = "/file/11099511675844278231.jpg";
        System.out.println(path.substring(6));;
    }

}
