package com.yeonsu.settle.web.dto;

import com.yeonsu.settle.domain.bills.Bills;
import com.yeonsu.settle.domain.group.Group;
import com.yeonsu.settle.domain.user.User;

import java.time.LocalDateTime;
import java.util.Set;

public class BillsListResponseDto {
    private Long id;
    private Group group;
    private String product;
    private Long amount;
    private User payer;
    private Set<User> participants;
    private LocalDateTime dateTime;
    private String memo;

    public BillsListResponseDto(Bills entity) {
        this.id = entity.getId();
        this.group = entity.getGroup();
        this.product = entity.getProduct();
        this.amount = entity.getAmount();
        this.payer = entity.getPayer();
        this.participants = entity.getParticipants();
        this.dateTime = entity.getDateTime();
        this.memo = entity.getMemo();
    }
}
