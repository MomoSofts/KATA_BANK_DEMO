package com.demo.bank.services;

import com.demo.bank.beans.ClientAccount;
import com.demo.bank.beans.ClientAccountStatement;
import com.demo.bank.beans.enums.OPERATION;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface IService {
    public void feedPrimaryData(List<ClientAccount> accounts);
    public ResponseEntity<?> deposit(String accountNumber, BigDecimal amount);
    public ResponseEntity<?> withdraw(String accountNumber, BigDecimal amount);

    public ResponseEntity<List<ClientAccountStatement>> accountStatement(String accountNumber, OPERATION operation, Date date, BigDecimal amount, BigDecimal balance);

    public ResponseEntity<List<ClientAccountStatement>> accountStatement(OPERATION operation, Date date, BigDecimal amount, BigDecimal balance);
}
