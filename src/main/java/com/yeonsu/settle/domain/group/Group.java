package com.yeonsu.settle.domain.group;

import com.yeonsu.settle.domain.BaseTimeEntity;
import com.yeonsu.settle.domain.bills.Bills;
import com.yeonsu.settle.domain.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@Table(name = "groups")  // "group" 는 H2 데이터베이스의 예약어임. -> 큰따옴표로 감싸주거나 변경 필요
@Entity
public class Group extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "group_members",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> members;


    @OneToMany(mappedBy = "group", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Bills> bills = new ArrayList<>();

    @Builder
    public Group(String name, Set<User> members) {
        this.name = name;
        this.members = members;

        for (User member:members) {
            member.addGroup(this);
        }
    }

    public void addBill(Bills bill) {
        bills.add(bill);
    }

    public void removeBill(Bills bill) {
        bills.remove(bill);
    }
}
