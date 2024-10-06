package com.ust.income.service;


import com.ust.income.model.Income;
import com.ust.income.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    public double getTotalSpent(Date date) {
        List<Income> incomes = incomeRepository.findByCreateDate(date);
        return incomes.stream().mapToDouble(Income::getAmt).sum();
    }

    public Map<String, Double> getAmountByPaymentType(Date date) {
        List<Income> incomes = incomeRepository.findByCreateDate(date);
        Map<String, Double> result = new HashMap<>();
        for (Income income : incomes) {
            result.merge(income.getPaymentType(), income.getAmt(), Double::sum);
        }
        return result;
    }

    public double getTotalSpentForMonth(Date month) {
        // Set the start of the month
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date startDate = calendar.getTime();

        // Set the end of the month
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = calendar.getTime();

        List<Income> incomes = incomeRepository.findByCreateDateBetween(startDate, endDate);
        return incomes.stream().mapToDouble(Income::getAmt).sum();
    }

    public Income saveExpense(Income income) {
        return incomeRepository.save(income);
    }
}
