package com.ust.income.controller;

import com.ust.income.model.Income;
import com.ust.income.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/income")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @PostMapping("/add")
    public ResponseEntity<Income> addIncome(@RequestBody Income income) {

        Income savedIncome = incomeService.saveExpense(income);
        return new ResponseEntity<>(savedIncome, HttpStatus.CREATED);
    }

    @GetMapping("/totalSpent")
    public double getTotalSpent(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return incomeService.getTotalSpent(date);
    }

    @GetMapping("/amountByPaymentType")
    public Map<String, Double> getAmountByPaymentType(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return incomeService.getAmountByPaymentType(date);
    }

    @GetMapping("/totalSpentForMonth")
    public double getTotalSpentForMonth(@RequestParam("month") @DateTimeFormat(pattern = "yyyy-MM-dd") Date month) {
        return incomeService.getTotalSpentForMonth(month);
    }

}
