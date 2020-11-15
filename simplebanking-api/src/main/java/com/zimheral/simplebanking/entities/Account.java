package com.zimheral.simplebanking.entities;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import javax.persistence.OneToMany;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
