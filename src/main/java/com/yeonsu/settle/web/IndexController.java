package com.yeonsu.settle.web;

import com.yeonsu.settle.config.auth.LoginUser;
import com.yeonsu.settle.config.auth.dto.SessionUser;
import com.yeonsu.settle.service.bills.BillsService;
import com.yeonsu.settle.web.dto.BillsResponseDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final BillsService billsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {  // @LoginUser annotation 제작
        model.addAttribute("bills", billsService.findAllDesc());

        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/bills/save")
    public String billsSave() {
        return "bills-save";
    }

    @GetMapping("/bills/update/{id}")
    public String billsUpdate(@PathVariable("id") Long id, Model model) {
        BillsResponseDto dto = billsService.findById(id);
        model.addAttribute("bill", dto);

        return "bills-update";
    }
}
