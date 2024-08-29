package com.yeonsu.settle.web;

import com.yeonsu.settle.service.bills.BillsService;
import com.yeonsu.settle.web.dto.BillsResponseDto;
import com.yeonsu.settle.web.dto.BillsSaveRequestDto;
import com.yeonsu.settle.web.dto.BillsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class BillsApiController {
    private final BillsService billsService;

    @PostMapping("/api/v1/bills")
    public Long save(@RequestBody BillsSaveRequestDto requestDto) {
        return billsService.save(requestDto);
    }

    @PutMapping("/api/v1/bills/{id}")
    public Long update(@PathVariable("id") Long id, @RequestBody BillsUpdateRequestDto requestDto) {
        return billsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/bills/{id}")
    public BillsResponseDto findById(@PathVariable("id") Long id) {
        return billsService.findById(id);
    }
}
