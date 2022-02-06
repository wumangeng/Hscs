package com.hscs;

import com.hscs.commonutils.MD5;
import org.junit.Test;

/**
 * @Author Painter
 * @Description TODO
 * @Date 2021/3/4 20:34
 **/


public class test {

    @Test
    public static void main(String[] args) {
        System.out.println("密码：  "+MD5.encrypt("111111"));
    }

}
