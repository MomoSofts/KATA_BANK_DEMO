package com.demo.bank.services;

import com.demo.bank.beans.ClientAccount;
import com.demo.bank.beans.ClientAccountStatement;
import com.demo.bank.beans.enums.CATEGORY;
import com.demo.bank.beans.enums.OPERATION;
import com.demo.bank.repositories.ClientAccountRepository;
import com.demo.bank.repositories.ClientAccountStatementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class ServiceImplTest {
    @Autowired
    private ClientAccountRepository clientAccountRepository;
    @Autowired
    private  ClientAccountStatementRepository clientAccountStatementRepository;

    private ServiceImpl serviceImplUnderTest;

    @BeforeEach
    void setUp() {
        serviceImplUnderTest = new ServiceImpl(clientAccountRepository, clientAccountStatementRepository);
    }

    @Test
    void testFeedPrimaryData() {
        final ClientAccount clientAccount = new ClientAccount();
        clientAccount.setAccountNumber("CC014279863275461238");
        clientAccount.setName("name");
        clientAccount.setSurname("surname");
        clientAccount.setBalance(new BigDecimal("2000.00"));
        clientAccount.setCategory(CATEGORY.CURRENT);
        final List<ClientAccount> accounts = List.of(clientAccount);
        List<ClientAccount> clientAccounts = clientAccountRepository.saveAll(accounts);
        clientAccounts.forEach(x -> {
            assertThat(x).hasFieldOrPropertyWithValue("accountNumber", "CC014279863275461238");
            assertThat(x).hasFieldOrPropertyWithValue("name", "name");
            assertThat(x).hasFieldOrPropertyWithValue("surname", "surname");
            assertThat(x).hasFieldOrPropertyWithValue("balance", new BigDecimal("2000.00"));
            assertThat(x).hasFieldOrPropertyWithValue("category", CATEGORY.CURRENT);
        });
    }

    @Test
    void testDeposit() {
        final ClientAccount clientAccount1 = new ClientAccount();
        clientAccount1.setAccountNumber("CC014279863275461239");
        clientAccount1.setName("name");
        clientAccount1.setSurname("surname");
        clientAccount1.setBalance(new BigDecimal("2000.00"));
        clientAccount1.setCategory(CATEGORY.CURRENT);
        clientAccountRepository.save(clientAccount1);
        ClientAccount clientAccount = clientAccountRepository.save(clientAccount1);
        assertThat(clientAccount).hasFieldOrPropertyWithValue("name", "name");
        assertThat(clientAccount).hasFieldOrPropertyWithValue("surname","surname");
        assertThat(clientAccount).hasFieldOrPropertyWithValue("balance",new BigDecimal("2000.00"));
        assertThat(clientAccount).hasFieldOrPropertyWithValue("category", CATEGORY.CURRENT);

        final ClientAccountStatement clientAccountStatement = new ClientAccountStatement();
        clientAccountStatement.setAccountNumber("CC014279863275461239");
        clientAccountStatement.setTransactionAmount(new BigDecimal("2000.00"));
        clientAccountStatement.setBalance(new BigDecimal("2000.00"));
        clientAccountStatement.setOperation(OPERATION.DEPOSIT);
        assertThat(clientAccountStatement).hasFieldOrPropertyWithValue("accountNumber", "CC014279863275461239");
        assertThat(clientAccountStatement).hasFieldOrPropertyWithValue("transactionAmount",new BigDecimal("2000.00"));
        assertThat(clientAccountStatement).hasFieldOrPropertyWithValue("balance",new BigDecimal("2000.00"));
        assertThat(clientAccountStatement).hasFieldOrPropertyWithValue("operation",OPERATION.DEPOSIT);
        final ResponseEntity<?> result = serviceImplUnderTest.deposit("CC014279863275461239", new BigDecimal("2000.00"));
        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Test
    void testWithdraw() {
        final ClientAccount clientAccount1 = new ClientAccount();
        clientAccount1.setAccountNumber("CC014279863275461239");
        clientAccount1.setName("name");
        clientAccount1.setSurname("surname");
        clientAccount1.setBalance(new BigDecimal("2000.00"));
        clientAccount1.setCategory(CATEGORY.CURRENT);
        clientAccountRepository.save(clientAccount1);
        ClientAccount clientAccount = clientAccountRepository.save(clientAccount1);
        assertThat(clientAccount).hasFieldOrPropertyWithValue("name", "name");
        assertThat(clientAccount).hasFieldOrPropertyWithValue("surname","surname");
        assertThat(clientAccount).hasFieldOrPropertyWithValue("balance",new BigDecimal("2000.00"));
        assertThat(clientAccount).hasFieldOrPropertyWithValue("category", CATEGORY.CURRENT);

        final ClientAccountStatement clientAccountStatement = new ClientAccountStatement();
        clientAccountStatement.setAccountNumber("CC014279863275461239");
        clientAccountStatement.setTransactionAmount(new BigDecimal("2000.00"));
        clientAccountStatement.setBalance(new BigDecimal("2000.00"));
        clientAccountStatement.setOperation(OPERATION.WITHDRAWAL);
        assertThat(clientAccountStatement).hasFieldOrPropertyWithValue("accountNumber", "CC014279863275461239");
        assertThat(clientAccountStatement).hasFieldOrPropertyWithValue("transactionAmount",new BigDecimal("2000.00"));
        assertThat(clientAccountStatement).hasFieldOrPropertyWithValue("balance",new BigDecimal("2000.00"));
        assertThat(clientAccountStatement).hasFieldOrPropertyWithValue("operation",OPERATION.WITHDRAWAL);
        final ResponseEntity<?> result = serviceImplUnderTest.deposit("CC014279863275461239", new BigDecimal("2000.00"));
        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Test
    void testOperation() {
       testDeposit();
       testWithdraw();
    }

}
