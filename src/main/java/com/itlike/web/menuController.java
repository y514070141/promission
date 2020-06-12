package com.itlike.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class menuController {
    @RequestMapping("/menu")
    public String employee(){
        return "menu";
    }
}
