package com.yeonsu.settle.domain.bills;

import com.yeonsu.settle.domain.BaseTimeEntity;
import com.yeonsu.settle.domain.group.Group;
import com.yeonsu.settle.domain.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@DynamicUpdate // 변경한 필드만 대응
@Entity
public class Bills extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Group group;

    @Column(length = 500, nullable = false)
    private String product;

    @Column(nullable = false)
    private Long amount;

    @ManyToOne
    private User payer;

    @ManyToMany
    @JoinTable(
            name = "bill_participants",
            joinColumns = @JoinColumn(name = "bill_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> participants;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(columnDefinition = "TEXT")
    private String memo;

    @Builder
    public Bills(Group group, String product, Long amount, User payer, Set<User> participants, LocalDateTime dateTime, String memo) {
        this.group = group;
        this.product = product;
        this.amount = amount;
        this.payer = payer;
        this.participants = participants;
        this.dateTime = dateTime;
        this.memo = memo;

    }

    public void update(String product, Long amount, User payer, Set<User> participants, LocalDateTime dateTime, String memo) {
        this.product = product;
        this.amount = amount;
        this.payer = payer;
        this.participants = participants;
        this.dateTime = dateTime;
        this.memo = memo;
    }
}

