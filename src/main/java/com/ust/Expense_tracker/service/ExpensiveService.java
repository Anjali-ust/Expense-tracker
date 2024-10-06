package com.ust.Expense_tracker.service;


import com.ust.Expense_tracker.dto.Expensivedto;
import com.ust.Expense_tracker.exception.ExpensiveNotFoundException;
import com.ust.Expense_tracker.model.Expensive;
import com.ust.Expense_tracker.repo.Expensiverepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ExpensiveService {

    @Autowired
    private Expensiverepo expensiverepo;

    public Expensive addExpensive(Expensivedto dto) {
        Expensive expensive = new Expensive();
        // Map DTO properties to Expensive entity properties
        expensive.setName(dto.getName());
        expensive.setTransactionType(dto.getTransactionType());
        expensive.setAmount(dto.getAmount());
        expensive.setDescription(dto.getDescription());
        return expensiverepo.save(expensive);
    }

    public Expensive getExpensiveById(UUID id) throws ExpensiveNotFoundException {
        Expensive expensive = expensiverepo.findById(id)
                .orElseThrow(() -> new ExpensiveNotFoundException("Expensive not found with id"+id));
        return expensive;
    }

    public List<Expensive> getAllExpenses() throws ExpensiveNotFoundException {
        List<Expensive> expenses  = expensiverepo.findAll();

        if(expenses.isEmpty()) {
            throw new ExpensiveNotFoundException("No expenses found");
        }
       return expenses;
    }

    // 1. Retrieve total amount spent on a particular date
    public double getTotalAmountOnDate(Date date) {
        List<Expensive> expenses = expensiverepo.findByDate(date);
        return expenses.stream()
                .filter(expense -> expense.getTransactionType().equalsIgnoreCase("expense"))
                .mapToDouble(Expensive::getAmount)
                .sum();
    }

    // 2. Retrieve amount spent on each category on a particular date
    public double getAmountSpentOnCategoryByDate(Date date, String category) {
        List<Expensive> expenses = expensiverepo.findByDate(date);
        return expenses.stream()
                .filter(expense -> expense.getName().equalsIgnoreCase(category))
                .mapToDouble(Expensive::getAmount)
                .sum();
    }

    // 3. Retrieve total amount spent in a particular month
    public double getTotalAmountOnMonth(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        Date startDate = calendar.getTime();

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = calendar.getTime();

        List<Expensive> expenses = expensiverepo.findByDateBetween(startDate, endDate);
        return expenses.stream()
                .filter(expense -> expense.getTransactionType().equalsIgnoreCase("expense"))
                .mapToDouble(Expensive::getAmount)
                .sum();
    }

    // 4. Retrieve current balance on a particular date
    public double getBalanceOnDate(Date date) {
        List<Expensive> expenses = expensiverepo.findByDate(date);
        double income = expenses.stream()
                .filter(expense -> expense.getTransactionType().equalsIgnoreCase("income"))
                .mapToDouble(Expensive::getAmount)
                .sum();

        double expensesAmount = expenses.stream()
                .filter(expense -> expense.getTransactionType().equalsIgnoreCase("expense"))
                .mapToDouble(Expensive::getAmount)
                .sum();

        return income - expensesAmount;
    }

    // 5. Retrieve income, expense, and total balance on a particular month and year
    public double[] getMonthlyIncomeExpenseBalance(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        Date startDate = calendar.getTime();

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = calendar.getTime();

        List<Expensive> expenses = expensiverepo.findByDateBetween(startDate, endDate);

        double income = expenses.stream()
                .filter(expense -> expense.getTransactionType().equalsIgnoreCase("income"))
                .mapToDouble(Expensive::getAmount)
                .sum();

        double expensesAmount = expenses.stream()
                .filter(expense -> expense.getTransactionType().equalsIgnoreCase("expense"))
                .mapToDouble(Expensive::getAmount)
                .sum();

        return new double[]{income, expensesAmount, income - expensesAmount};
    }
}
