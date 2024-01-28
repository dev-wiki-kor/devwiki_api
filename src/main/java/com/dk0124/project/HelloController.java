package com.dk0124.project;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/*
* 보안 구현 중, 테스트 api
* */
@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/attackGet")
    public String getHello(){
        return "hello";
    }

    @PostMapping("/attackPost")
    public String postHello(){
        return "postHello";
    }
}

