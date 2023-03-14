package com.example.school_manage.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author macro
 * @date 2022/12/15
 */
@RestController
@RequestMapping("/user")
public class TestController {

    @GetMapping
    public Map<String, Object> Test(){
        Map<String, Object> map = new HashMap<>();
        map.put("data",1);
        return map;
    }

}
