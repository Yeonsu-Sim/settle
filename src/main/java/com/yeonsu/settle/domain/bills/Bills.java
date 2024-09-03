package com.yeonsu.settle.domain.bills;

import com.yeonsu.settle.domain.BaseTimeEntity;
import com.yeonsu.settle.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@DynamicUpdate // 변경한 필드만 대응
@Entity
public class Bills extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String product;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private String payer;

    @Column(nullable = false)
    private List<String> participants;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(columnDefinition = "TEXT")
    private String memo;

    @Builder
    public Bills(String product, Long amount, String payer, List<String> participants, LocalDateTime dateTime, String memo) {
        this.product = product;
        this.amount = amount;
        this.payer = payer;
        this.participants = participants;
        this.dateTime = dateTime;
        this.memo = memo;

    }

    public void update(String product, Long amount, String payer, List<String> participants, LocalDateTime dateTime, String memo) {
        this.product = product;
        this.amount = amount;
        this.payer = payer;
        this.participants = participants;
        this.dateTime = dateTime;
        this.memo = memo;
    }
}

