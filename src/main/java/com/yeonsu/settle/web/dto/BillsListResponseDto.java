package com.yeonsu.settle.web.dto;

import com.yeonsu.settle.domain.bills.Bills;

import java.time.LocalDateTime;
import java.util.List;

public class BillsListResponseDto {
    private Long id;
    private String product;
    private Long amount;
    private String payer;
    private List<String> participants;
    private LocalDateTime dateTime;
    private String memo;

    public BillsListResponseDto(Bills entity) {
        this.id = entity.getId();
        this.product = entity.getProduct();
        this.amount = entity.getAmount();
        this.payer = entity.getPayer();
        this.participants = entity.getParticipants();
        this.dateTime = entity.getDateTime();
        this.memo = entity.getMemo();
    }
}
