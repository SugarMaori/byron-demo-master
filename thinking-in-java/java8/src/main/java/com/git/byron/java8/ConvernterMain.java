package com.git.byron.java8;

/**
 * @Author: byron
 * @ProjectName: byron-demo-master
 * @Package: com.git.byron.java8
 * @ClassName: ConvernterMain
 * @Description:
 * @Date: 2019/8/12 11:57
 * @Version: 1.0
 */
public class ConvernterMain {

    public String test(){
        return "Hello";
    }

    public static void main(String[] agsr){

        Converter<String,String> converter = (from) -> String.valueOf(from);
        System.out.println(converter.convert("123"));
    }
}
