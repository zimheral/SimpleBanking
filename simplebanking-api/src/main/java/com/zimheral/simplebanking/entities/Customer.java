package com.zimheral.simplebanking.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Customer {

    @Id
//    @SequenceGenerator(name = "seq_customer", sequenceName = "seq_customer")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String surname;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "customer")
    private Account account;
}
