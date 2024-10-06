package com.ust.Expense_tracker.repo;

import com.ust.Expense_tracker.model.Expensive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface Expensiverepo extends JpaRepository<Expensive, UUID> {
    List<Expensive> findByDate(Date date);

    List<Expensive> findByDateBetween(Date startDate, Date endDate);
}
