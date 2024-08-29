package com.yeonsu.settle.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class BillsUpdateRequestDto {
    private String product;
    private Long amount;
    private String payer;
    private List<String> participants;
    private LocalDateTime dateTime;
    private String memo;

    @Builder
    public BillsUpdateRequestDto(String product, Long amount, String payer, List<String> participants, LocalDateTime dateTime, String memo) {
        this.product = product;
        this.amount = amount;
        this.payer = payer;
        this.participants = participants;
        this.dateTime = dateTime;
        this.memo = memo;
    }
}
