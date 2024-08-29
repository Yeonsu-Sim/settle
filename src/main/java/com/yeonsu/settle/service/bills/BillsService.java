package com.yeonsu.settle.service.bills;

import com.yeonsu.settle.domain.bills.Bills;
import com.yeonsu.settle.domain.bills.BillsRepository;
import com.yeonsu.settle.web.dto.BillsResponseDto;
import com.yeonsu.settle.web.dto.BillsSaveRequestDto;
import com.yeonsu.settle.web.dto.BillsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BillsService {
    private final BillsRepository billsRepository;

    @Transactional
    public Long save(BillsSaveRequestDto requestDto) {
        return billsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, BillsUpdateRequestDto requestDto) {
        Bills bills = billsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 영수증이 없습니다. id=" + id));

        bills.update(requestDto.getProduct(), requestDto.getAmount(), requestDto.getPayer(),
                requestDto.getParticipants(), requestDto.getDateTime(), requestDto.getMemo());

        return id;
    }

    public BillsResponseDto findById(Long id) {
        Bills entity = billsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 영수증이 없습니다. id=" + id));

        return new BillsResponseDto(entity);
    }
}
