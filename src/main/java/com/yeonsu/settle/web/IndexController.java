package com.yeonsu.settle.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/bills/save")
    public String billsSave() {
        return "bills-save";
    }
}
