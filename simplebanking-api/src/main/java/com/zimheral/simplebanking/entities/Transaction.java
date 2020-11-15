package com.zimheral.simplebanking.entities;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class Transaction {

    @Id
    @SequenceGenerator(name = "seq_transaction", sequenceName = "seq_transaction")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_transaction")
    private Long id;

    @NonNull
    private BigDecimal amount;

    @NonNull
    @ManyToOne
    private Account account;
}
