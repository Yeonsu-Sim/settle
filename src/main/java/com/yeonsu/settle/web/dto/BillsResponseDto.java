package com.yeonsu.settle.web.dto;

import com.yeonsu.settle.domain.bills.Bills;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class BillsResponseDto {
    private Long id;
    private String product;
    private Long amount;
    private String payer;
    private List<String> participants;
    private LocalDateTime dateTime;
    private String memo;

    public BillsResponseDto(Bills entity) {
        this.id = entity.getId();
        this.product = entity.getProduct();
        this.amount = entity.getAmount();
        this.payer = entity.getPayer();
        this.participants = entity.getParticipants();
        this.dateTime = entity.getDateTime();
        this.memo = entity.getMemo();

    }
}
