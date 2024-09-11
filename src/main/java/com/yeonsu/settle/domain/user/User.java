package com.yeonsu.settle.domain.user;

import com.yeonsu.settle.domain.BaseTimeEntity;
import com.yeonsu.settle.domain.group.Group;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Table(name = "users")  // "user" 는 H2 데이터베이스의 예약어임. -> 큰따옴표로 감싸주거나 변경 필요
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

//    @Column(nullable = false)
//    private String account;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @ManyToMany(mappedBy = "members")
    private Set<Group> groups = new HashSet<>();

    @Builder
    public User(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
//        this.account = account;
        this.picture = picture;
        this.role = role;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;
        return this;
    }

    public void addGroup(Group group) {
        groups.add(group);
    }

    public void removeGroup(Group group) {
        groups.remove(group);
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
