package com.ust.Expense_tracker.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expensivedto {

    @NotNull
    private String name;

    @NotBlank(message = "Transaction type is mandatory")
    @Pattern(regexp = "income|expense", message = "Transaction type must be either 'income' or 'expense'")
    private String transactionType;

    @Min(value = 1, message = "Amount must be greater than 0")
    private double amount;

    @Size(min = 5, max = 100, message = "Description must be between 3 and 100 characters")
    private String description;

    @NotNull(message = "Date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")  // Ensure correct format
    private Date date;

}
