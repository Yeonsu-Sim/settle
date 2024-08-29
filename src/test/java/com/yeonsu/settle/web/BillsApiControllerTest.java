package com.yeonsu.settle.web;

import com.yeonsu.settle.domain.bills.Bills;
import com.yeonsu.settle.domain.bills.BillsRepository;
import com.yeonsu.settle.web.dto.BillsSaveRequestDto;
import com.yeonsu.settle.web.dto.BillsUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BillsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BillsRepository billsRepository;

    @After
    public void tearDown() throws Exception {
        billsRepository.deleteAll();
    }

    @Test
    public void Bills_등록된다() throws Exception {
        // given
        String product = "테스트 영수증";
        Long amount = 10000L;
        String payer = "연수";
        LocalDateTime dateTime = LocalDateTime.now();

        List<String> participants = new ArrayList<String>();
        participants.add("연수");
        participants.add("연화");
        participants.add("윤성");

        BillsSaveRequestDto requestDto = BillsSaveRequestDto.builder()
                .product(product)
                .amount(amount)
                .payer(payer)
                .participants(participants)
                .dateTime(dateTime)
                .memo(null)
                .build();

        String url = "http://localhost:" + port + "/api/v1/bills";

        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Bills> all = billsRepository.findAll();
        assertThat(all.get(0).getPayer()).isEqualTo(payer);
        assertThat(all.get(0).getAmount()).isEqualTo(amount);
    }

    @Test
    public void Bills_수정된다() throws Exception {
        // given
        String product = "테스트 영수증";
        Long amount = 10000L;
        String payer = "연수";
        LocalDateTime dateTime = LocalDateTime.now();

        List<String> participants = new ArrayList<String>();
        participants.add("연수");
        participants.add("연화");
        participants.add("윤성");

        Bills savedBills = billsRepository.save(Bills.builder()
                .product(product)
                .amount(amount)
                .payer(payer)
                .participants(participants)
                .dateTime(dateTime)
                .memo(null)
                .build());

        Long updateId = savedBills.getId();
        String expectedProduct = "테스트용 영수증";
        String expectedPayer = "연화";

        BillsUpdateRequestDto requestDto = BillsUpdateRequestDto.builder()
                .product(expectedProduct)
                .amount(amount)
                .payer(expectedPayer)
                .participants(participants)
                .dateTime(dateTime)
                .memo(null)
                .build();

        String url = "http://localhost:" + port + "/api/v1/bills/" + updateId;

        HttpEntity<BillsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        // when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Bills> all = billsRepository.findAll();
        assertThat(all.get(0).getProduct()).isEqualTo(expectedProduct);
        assertThat(all.get(0).getPayer()).isEqualTo(expectedPayer);
    }
}
