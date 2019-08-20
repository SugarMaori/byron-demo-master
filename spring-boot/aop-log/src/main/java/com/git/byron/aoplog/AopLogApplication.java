package com.git.byron.aoplog;

import com.git.byron.aoplog.annotation.LogAnnotation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class AopLogApplication {

    public static void main(String[] args) {
        SpringApplication.run(AopLogApplication.class, args);
    }

    @LogAnnotation()
    @PostMapping(value = "get")
    public Map<String,String> test(@RequestParam(value = "name") String name,
                                   @RequestParam(value = "sex") String sex){
        return new LinkedHashMap<String,String>(){
            {
                put("name",name);
                put("sex",sex);
            }
        };
    }

}
