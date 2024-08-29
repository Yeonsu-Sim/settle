package com.yeonsu.settle.web.dto;

import com.yeonsu.settle.domain.bills.Bills;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class BillsSaveRequestDto {
    private String product;
    private Long amount;
    private String payer;
    private List<String> participants;
    private LocalDateTime dateTime;
    private String memo;

    @Builder
    public BillsSaveRequestDto(String product, Long amount, String payer, List<String> participants, LocalDateTime dateTime, String memo) {
        this.product = product;
        this.amount = amount;
        this.payer = payer;
        this.participants = participants;
        this.dateTime = dateTime;
        this.memo = memo;
    }

    public Bills toEntity() {
        return Bills.builder()
                .product(product)
                .amount(amount)
                .payer(payer)
                .participants(participants)
                .dateTime(dateTime)
                .memo(memo)
                .build();
    }
}
