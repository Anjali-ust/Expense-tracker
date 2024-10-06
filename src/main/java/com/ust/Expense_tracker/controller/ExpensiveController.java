package com.ust.Expense_tracker.controller;


import com.ust.Expense_tracker.dto.Expensivedto;
import com.ust.Expense_tracker.exception.ExpensiveNotFoundException;
import com.ust.Expense_tracker.model.Expensive;
import com.ust.Expense_tracker.service.ExpensiveService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("expensive")
public class ExpensiveController {


    @Autowired
    private ExpensiveService expensiveService;

    // Implement CRUD operations for Expensive entity
    @PostMapping("/addexpensive")
    public ResponseEntity<Expensive> addExpensive(@RequestBody @Valid Expensivedto dto) {
        // Implement logic to add a new Expensive entity
        return new ResponseEntity<>(expensiveService.addExpensive(dto), HttpStatus.CREATED);
    }

    @GetMapping("/getexpensivebyid/{id}")
    public ResponseEntity<Expensive> getExpensiveById(@PathVariable UUID id) throws ExpensiveNotFoundException {
        // Implement logic to retrieve an Expensive entity by ID
        return ResponseEntity.ok(expensiveService.getExpensiveById(id));
    }

    @GetMapping("/getallexpenses")
    public ResponseEntity<List<Expensive>> getAllExpenses() throws ExpensiveNotFoundException {
        return ResponseEntity.ok(expensiveService.getAllExpenses());
    }

    // 1. Retrieve total amount spent on a particular date
    @GetMapping("/total/{date}")
    public double getTotalAmountOnDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return expensiveService.getTotalAmountOnDate(date);
    }

    // 2. Retrieve amount spent on each category on a particular date
    @GetMapping("/total/{date}/{category}")
    public double getAmountSpentOnCategoryByDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                                 @PathVariable String category) {
        return expensiveService.getAmountSpentOnCategoryByDate(date, category);
    }

    // 3. Retrieve total amount spent in a particular month
    @GetMapping("/total/{month}/{year}")
    public double getTotalAmountOnMonth(@PathVariable int month, @PathVariable int year) {
        return expensiveService.getTotalAmountOnMonth(month, year);
    }

    // 4. Retrieve current balance on a particular date
    @GetMapping("/balance/{date}")
    public double getBalanceOnDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return expensiveService.getBalanceOnDate(date);
    }

    // 5. Retrieve income, expense, and total balance on a particular month and year
    @GetMapping("/balance/{month}/{year}")
    public double[] getMonthlyIncomeExpenseBalance(@PathVariable int month, @PathVariable int year) {
        return expensiveService.getMonthlyIncomeExpenseBalance(month, year);
    }
}
