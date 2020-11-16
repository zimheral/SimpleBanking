package com.zimheral.simplebanking.services;

import com.zimheral.simplebanking.entities.Customer;
import com.zimheral.simplebanking.entities.Transaction;
import com.zimheral.simplebanking.model.Account;
import com.zimheral.simplebanking.model.Credit;
import com.zimheral.simplebanking.repositories.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private CustomerService customerService;
    @Mock
    private TransactionService transactionService;
    @InjectMocks
    private AccountService accountService;

    @Test
    void shouldReturnPersistedAccount() {
        //GIVEN
        Credit credit = new Credit().credit(BigDecimal.ZERO);
        long customerId = 12345L;
        Customer customer = mock(Customer.class);
        String iban = "BE12345";
        Optional<com.zimheral.simplebanking.entities.Account> accountDB = Optional.of(
                com.zimheral.simplebanking.entities.Account
                        .builder().iban(iban).customer(customer).balance(credit.getCredit()).accountType(Account.AccountTypeEnum.CURRENT_ACCOUNT)
                        .build());

        when(customerService.getCustomer(customerId)).thenReturn(customer);
        when(accountRepository.findByCustomer(customer)).thenReturn(accountDB);

        //WHEN
        Account account = accountService.processOpenAccount(customerId, credit);

        //THEN
        assertNotNull(account);
        assertEquals(iban, account.getIban());
        assertEquals(Account.AccountTypeEnum.CURRENT_ACCOUNT, account.getAccountType());
        assertEquals(credit.getCredit(), account.getBalance());
        verify(transactionService, never()).processTransaction(any(BigDecimal.class), any(com.zimheral.simplebanking.entities.Account.class));
    }

    @Test
    void shouldGenerateNewAccount() {

        //GIVEN
        Credit credit = new Credit().credit(BigDecimal.ZERO);
        long customerId = 12345L;
        Customer customer = mock(Customer.class);
        String iban = "BE12345";
        com.zimheral.simplebanking.entities.Account savedAccount = com.zimheral.simplebanking.entities.Account
                .builder().iban(iban).customer(customer).balance(credit.getCredit()).accountType(Account.AccountTypeEnum.CURRENT_ACCOUNT)
                .build();

        when(customerService.getCustomer(customerId)).thenReturn(customer);
        when(accountRepository.findByCustomer(customer)).thenReturn(Optional.empty());
        when(accountRepository.saveAndFlush(any(com.zimheral.simplebanking.entities.Account.class))).thenReturn(savedAccount);


        //WHEN
        Account account = accountService.processOpenAccount(customerId, credit);

        //THEN
        assertNotNull(account);
        assertEquals(iban, account.getIban());
        assertEquals(Account.AccountTypeEnum.CURRENT_ACCOUNT, account.getAccountType());
        assertEquals(credit.getCredit(), account.getBalance());
        verify(transactionService, never()).processTransaction(any(BigDecimal.class), any(com.zimheral.simplebanking.entities.Account.class));
    }

    @Test
    void shouldProcessTransactionWhenCreditMoreThan0() {
        //GIVEN
        Credit credit = new Credit().credit(BigDecimal.valueOf(100));
        long customerId = 12345L;
        String iban = "BE12345";
        Customer customer = mock(Customer.class);
        com.zimheral.simplebanking.entities.Account savedAccount = com.zimheral.simplebanking.entities.Account
                .builder().iban(iban).customer(customer).balance(credit.getCredit()).accountType(Account.AccountTypeEnum.CURRENT_ACCOUNT)
                .build();
        Transaction transaction = Transaction.builder().account(savedAccount).amount(credit.getCredit()).build();

        when(customerService.getCustomer(customerId)).thenReturn(customer);
        when(accountRepository.findByCustomer(customer)).thenReturn(Optional.empty());
        when(accountRepository.saveAndFlush(any(com.zimheral.simplebanking.entities.Account.class))).thenReturn(savedAccount);
        when(transactionService.processTransaction(credit.getCredit(), savedAccount)).thenReturn(transaction);

        //WHEN
        Account account = accountService.processOpenAccount(customerId, credit);

        //THEN
        assertNotNull(account);
        assertEquals(credit.getCredit(), account.getBalance());
        verify(transactionService).processTransaction(credit.getCredit(), savedAccount);
    }

    @Test
    void shouldThrowExceptionWhenProcessOpenAccountWithCreditNegative() {

        //GIVEN
        Credit credit = new Credit();
        credit.setCredit(BigDecimal.valueOf(-10));
        long customerId = 12345L;

        //THEN
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> accountService.processOpenAccount(customerId, credit));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }
}