package com.zimheral.simplebanking.entities;

import com.zimheral.simplebanking.model.Account.AccountTypeEnum;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Account {

    @Id
    @SequenceGenerator(name = "seq_account", sequenceName = "seq_account")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_account")
    private Long id;

    @NonNull
    private String iban;

    @NonNull
    private BigDecimal balance;

    @NonNull
    @Enumerated(EnumType.STRING)
    private AccountTypeEnum accountType;

    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne
    private Customer customer;

}
