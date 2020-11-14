package com.zimheral.simplebanking.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long customerId;
    private String currentAccount;

    protected Account() {
    }

    public Account(Long customerId, String currentAccount) {
        this.customerId = customerId;
        this.currentAccount = currentAccount;
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getCurrentAccount() {
        return currentAccount;
    }

    @Override
    public String toString() {
        return String.format(
                "Account[id=%d, customerId='%s', currentAccount='%s']",
                id, customerId, currentAccount);
    }
}
