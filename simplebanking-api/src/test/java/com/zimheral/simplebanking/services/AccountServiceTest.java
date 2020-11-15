package com.zimheral.simplebanking.services;

import com.zimheral.simplebanking.entities.Account;
import com.zimheral.simplebanking.model.Credit;
import com.zimheral.simplebanking.model.CurrentAccount;
import com.zimheral.simplebanking.repositories.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    void shouldReturnPersistedAccountOnProcessOpenAccount() {
        //GIVEN
        Credit credit = new Credit().credit(BigDecimal.valueOf(100));
        long customerId = 12345L;
        String currentAccountIban = "BE12345";
        Optional<Account> account = Optional.of(new Account(customerId, currentAccountIban));
        when(accountRepository.findByCustomerId(customerId)).thenReturn(account);

        //WHEN
        CurrentAccount currentAccount = accountService.processOpenAccount(customerId, credit);

        //THEN
        assertNotNull(currentAccount.getAccount());
        assertEquals(currentAccountIban, currentAccount.getAccount());
    }

    @Test
    void shouldGenerateAccountOnProcessOpenAccount() {

        //GIVEN
        Credit credit = new Credit().credit(BigDecimal.ZERO);
        long customerId = 12345L;
        when(accountRepository.findByCustomerId(customerId)).thenReturn(Optional.empty());

        //WHEN
        CurrentAccount currentAccount = accountService.processOpenAccount(customerId, credit);

        //THEN
        assertNotNull(currentAccount.getAccount());
    }

    @Test
    void shouldThrowExceptionWhenProcessOpenAccountWithCreditNegative() {

        //GIVEN
        Credit credit = new Credit();
        credit.setCredit(BigDecimal.valueOf(-10));
        long customerId = 12345L;

        //THEN
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> accountService.processOpenAccount(customerId, credit));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }
}