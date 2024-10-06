package com.ust.income.repository;

import com.ust.income.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Integer> {
    List<Income> findByCreateDate(Date date);
    List<Income> findByCreateDateBetween(Date startDate, Date endDate);
}
