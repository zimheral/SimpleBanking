package com.zimheral.simplebanking.services;

import com.zimheral.simplebanking.model.Credit;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountServiceTest {

    private final AccountService accountService = new AccountService();

    @Test
    void shouldProcessOpenAccountWithCreditMoreThan0() {
        Credit credit = new Credit();
        credit.setCredit(100L);

        long customerId = 12345L;
        accountService.processOpenAccount(customerId, credit);
    }

    @Test
    void shouldProcessOpenAccountWithCredit0() {
        Credit credit = new Credit();
        credit.setCredit(0L);

        long customerId = 12345L;
        accountService.processOpenAccount(customerId, credit);
    }

    @Test
    void shouldThrowExceptionWhenProcessOpenAccountWithCreditNull() {

        long customerId = 12345L;
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> accountService.processOpenAccount(customerId, null));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void shouldThrowExceptionWhenProcessOpenAccountWithCreditNegative() {

        Credit credit = new Credit();
        credit.setCredit(-10L);
        long customerId = 12345L;
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> accountService.processOpenAccount(customerId, credit));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }
}