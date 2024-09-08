package com.yeonsu.settle.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.yeonsu.settle.domain.bills.Bills;
import com.yeonsu.settle.domain.bills.BillsRepository;
import com.yeonsu.settle.web.dto.BillsSaveRequestDto;
import com.yeonsu.settle.web.dto.BillsUpdateRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BillsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BillsRepository billsRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    @After
    public void tearDown() throws Exception {
        billsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles="USER")  // 인증된 모의 사용자를 생성하여 합법적인 API 요청 가정
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
        mvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(new ObjectMapper().registerModule(new JavaTimeModule())  // Java8 LocalDateTime 직렬화 문제 해결
                    .writeValueAsString(requestDto)))  // body 영역을 문자열로 표현하기 위해 문자열 JSON으로 변환
            .andExpect(status().isOk());

        // then
        List<Bills> all = billsRepository.findAll();
        assertThat(all.get(0).getPayer()).isEqualTo(payer);
        assertThat(all.get(0).getAmount()).isEqualTo(amount);
    }

    @Test
    @WithMockUser(roles="USER")
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
        mvc.perform(put(url)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(new ObjectMapper().registerModule(new JavaTimeModule())  // Java8 LocalDateTime 직렬화 문제 해결
                    .writeValueAsString(requestDto)))  // body 영역을 문자열로 표현하기 위해 문자열 JSON으로 변환
            .andExpect(status().isOk());

        // then
        List<Bills> all = billsRepository.findAll();
        assertThat(all.get(0).getProduct()).isEqualTo(expectedProduct);
        assertThat(all.get(0).getPayer()).isEqualTo(expectedPayer);
    }
}
