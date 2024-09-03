package com.yeonsu.settle.domain.bills;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BillsRepository extends JpaRepository<Bills, Long> {
    @Query("SELECT b FROM Bills b ORDER BY b.id DESC")
    List<Bills> findAllDesc();
}
