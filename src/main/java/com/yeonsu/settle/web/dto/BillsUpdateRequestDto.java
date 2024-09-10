package com.yeonsu.settle.web.dto;

import com.yeonsu.settle.domain.group.Group;
import com.yeonsu.settle.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@NoArgsConstructor
public class BillsUpdateRequestDto {
    private String product;
    private Long amount;
    private User payer;
    private Set<User> participants;
    private LocalDateTime dateTime;
    private String memo;

    @Builder
    public BillsUpdateRequestDto(String product, Long amount, User payer, Set<User> participants, LocalDateTime dateTime, String memo) {
        this.product = product;
        this.amount = amount;
        this.payer = payer;
        this.participants = participants;
        this.dateTime = dateTime;
        this.memo = memo;
    }
}
