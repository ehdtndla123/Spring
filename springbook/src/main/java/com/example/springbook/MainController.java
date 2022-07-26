package com.example.springbook;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @RequestMapping("/main")
    @ResponseBody
    public String index(){
        return "mainasdas";
    }

    @RequestMapping("/")
    public String root(){
        return "redirect:/dev/post/lsit";
    }
}
