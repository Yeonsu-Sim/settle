package com.yeonsu.settle.domain.bills;

import com.yeonsu.settle.domain.group.Group;
import com.yeonsu.settle.domain.group.GroupRepository;
import com.yeonsu.settle.domain.user.Role;
import com.yeonsu.settle.domain.user.User;
import com.yeonsu.settle.domain.user.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BillsRepositoryTest {

    @Autowired
    BillsRepository billsRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    GroupRepository groupRepository;

    @After
    public void cleanup() {
        billsRepository.deleteAll();
    }

    @Test
    public void 영수증저장_불러오기() {
        // given
        String product = "테스트 영수증";
        Long amount = 10000L;

        User payer = User.builder().name("연수").email("shenlianxiu@gmail.com").role(Role.GUEST).picture("this is not a picture yet").build();
        userRepository.save(payer);

        LocalDateTime dateTime = LocalDateTime.now();

        Set<User> participants = new HashSet<>();
        participants.add(payer);

        Group group = Group.builder().name("테스트 그룹").members(participants).build();
        groupRepository.save(group);


        billsRepository.save(Bills.builder()
                .group(group)
                .product(product)
                .amount(amount)
                .payer(payer)
                .participants(participants)
                .dateTime(dateTime)
                .memo(null)
                .build());

        // when
        List<Bills> billsList = billsRepository.findAll();

        // then
        Bills bills = billsList.get(0);
        assertThat(bills.getProduct()).isEqualTo(product);
        assertThat(bills.getPayer().getName()).isEqualTo(payer.getName());
    }

    @Test
    public void BaseTimeEntity_등록() {
        // given
        LocalDateTime now = LocalDateTime.of(2024, 8, 29, 0, 0, 0);

        String product = "테스트 영수증";
        Long amount = 10000L;

        User payer = User.builder().name("연수").email("shenlianxiu@gmail.com").role(Role.GUEST).picture("this is not a picture yet").build();
        userRepository.save(payer);

        LocalDateTime dateTime = LocalDateTime.now();

        Set<User> participants = new HashSet<>();
        participants.add(payer);

        Group group = Group.builder().name("테스트 그룹").members(participants).build();
        groupRepository.save(group);


        billsRepository.save(Bills.builder()
                .group(group)
                .product(product)
                .amount(amount)
                .payer(payer)
                .participants(participants)
                .dateTime(dateTime)
                .memo(null)
                .build());

        // when
        List<Bills> billsList = billsRepository.findAll();

        // then
        Bills bills = billsList.get(0);

        System.out.println(">>>>>>>>> createdDate=" + bills.getCreatedDate() +
                ", modifiedDate=" + bills.getModifiedDate());

        assertThat(bills.getCreatedDate()).isAfter(now);
        assertThat(bills.getModifiedDate()).isAfter(now);
    }
}
