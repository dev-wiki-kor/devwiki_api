package com.dk0124.project;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/get")
    public String getHello(){
        return "hello";
    }

    @PostMapping("/post")
    public String postHello(){
        return "postHello";
    }
}

