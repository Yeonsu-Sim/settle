package com.yeonsu.settle.domain.bills;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BillsRepositoryTest {

    @Autowired
    BillsRepository billsRepository;

    @After
    public void cleanup() {
        billsRepository.deleteAll();
    }

    @Test
    public void 영수증저장_불러오기() {
        // given
        String product = "테스트 영수증";
        Long amount = 10000L;
        String payer = "연수";
        LocalDateTime dateTime = LocalDateTime.now();

        List<String> participants = new ArrayList<String>();
        participants.add("연수");
        participants.add("연화");
        participants.add("윤성");

        billsRepository.save(Bills.builder()
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
        assertThat(bills.getPayer()).isEqualTo(payer);
    }

    @Test
    public void BaseTimeEntity_등록() {
        // given
        LocalDateTime now = LocalDateTime.of(2024, 8, 29, 0, 0, 0);

        String product = "테스트 영수증";
        Long amount = 10000L;
        String payer = "연수";
        LocalDateTime dateTime = LocalDateTime.now();

        List<String> participants = new ArrayList<String>();
        participants.add("연수");
        participants.add("연화");
        participants.add("윤성");

        billsRepository.save(Bills.builder()
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
