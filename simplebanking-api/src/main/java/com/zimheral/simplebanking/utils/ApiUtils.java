package com.zimheral.simplebanking.utils;

import com.zimheral.simplebanking.entities.Customer;
import com.zimheral.simplebanking.model.Account;
import com.zimheral.simplebanking.model.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class ApiUtils {

    private ApiUtils() {
    }

    public static void buildAccount(Account account, com.zimheral.simplebanking.entities.Account savedAccount) {
        account.setIban(savedAccount.getIban());
        account.setAccountType(savedAccount.getAccountType());
        account.setBalance(savedAccount.getBalance());
        account.setTransactions(convertTransactions(savedAccount.getTransactions()));
    }

    public static List<Transaction> convertTransactions(List<com.zimheral.simplebanking.entities.Transaction> transactionsEntity) {
        if (Objects.isNull(transactionsEntity)) return null;
        return transactionsEntity
                .stream()
                .map(transactionEntity -> new Transaction().id(transactionEntity.getId()).amount(transactionEntity.getAmount()))
                .collect(Collectors.toList());
    }

    public static com.zimheral.simplebanking.entities.Account buildAccountEntity(BigDecimal creditAmount, Customer customer) {
        return com.zimheral.simplebanking.entities.Account.builder()
                .iban(generateIban())
                .customer(customer)
                .balance(creditAmount)
                //TODO: Static value. Implement dynamic feature
                .accountType(Account.AccountTypeEnum.CURRENT_ACCOUNT)
                .build();
    }

    public static String generateIban() {
        String accountNumber;
        Random random = new Random();
        accountNumber = IntStream.range(0, 14)
                .mapToObj(i -> String.valueOf(random.nextInt(10)))
                .collect(Collectors.joining("", "BE", ""));
        return accountNumber;
    }
}
