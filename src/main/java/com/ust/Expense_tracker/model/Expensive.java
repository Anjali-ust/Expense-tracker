package com.ust.Expense_tracker.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "expensive")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expensive {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String transactionType;
    private double amount;
    private String description;

    @Temporal(TemporalType.DATE)
    private Date date;
}
