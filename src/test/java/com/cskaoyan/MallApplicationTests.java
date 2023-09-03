package com.cskaoyan;

import com.cskaoyan.utils.PasswordProcess;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
class MallApplicationTests {


    @Test
    void contextLoads() {
        String format = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        format += (long)((Math.random()*9+1)*100000)+"";
        System.out.println(format);
    }

    @Test
    public void test1() {
        String s1 = PasswordProcess.passwordProcess("mall123");
        String s2 = PasswordProcess.passwordProcess("123456");
        String s3 = PasswordProcess.passwordProcess("admin123");
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
        System.out.println(s1.length());
    }


    @Test
    public void test2(){
        BigDecimal a = new BigDecimal(10);
        BigDecimal b = new BigDecimal(5);
        a = a.multiply(b).multiply(new BigDecimal(3));
        System.out.println(a);

    }
}
