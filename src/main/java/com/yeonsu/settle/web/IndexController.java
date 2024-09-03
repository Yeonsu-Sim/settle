package com.yeonsu.settle.web;

import com.yeonsu.settle.service.bills.BillsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final BillsService billsService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("bills", billsService.findAllDesc());
        return "index";
    }

    @GetMapping("/bills/save")
    public String billsSave() {
        return "bills-save";
    }
}
