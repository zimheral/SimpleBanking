package com.zimheral.simplebanking.entities;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Account {

    @Id
    @SequenceGenerator(name = "seq_account", sequenceName = "seq_account")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_account")
    private Long id;

    @NonNull
    private Long customerId;

    @NonNull
    private String currentAccount;

    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions;

}
