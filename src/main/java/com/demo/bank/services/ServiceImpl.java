package com.demo.bank.services;

import com.demo.bank.beans.ClientAccount;
import com.demo.bank.beans.ClientAccountStatement;
import com.demo.bank.beans.enums.OPERATION;
import com.demo.bank.repositories.ClientAccountRepository;
import com.demo.bank.repositories.ClientAccountStatementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class ServiceImpl implements IService {

    @Value("${status.message.success}")
    private String statusMessageSuccess;

    @Value("${status.message.notfound}")
    private String statusMessageNotFound;

    @Value("${status.message.insufficient}")
    private String statusMessageInsufficient;

    private final ClientAccountRepository clientAccountRepository;
    private final ClientAccountStatementRepository clientAccountStatementRepository;
    @Autowired
    public ServiceImpl(ClientAccountRepository clientAccountRepository, ClientAccountStatementRepository clientAccountStatementRepository) {
        this.clientAccountRepository = clientAccountRepository;
        this.clientAccountStatementRepository = clientAccountStatementRepository;
    }
    @Override
    public void feedPrimaryData(List<ClientAccount> accounts) {
        clientAccountRepository.saveAll(accounts);
    }
    @Override
    public ResponseEntity<?> deposit(String accountNumber, BigDecimal amount) {
        return operation(accountNumber, amount, OPERATION.DEPOSIT);
    }
    @Override
    public ResponseEntity<?> withdraw(String accountNumber, BigDecimal amount) {
        return operation(accountNumber, amount, OPERATION.WITHDRAWAL);
    }

    @Transactional(rollbackOn = Exception.class)
    ResponseEntity<?> operation(String accountNumber, BigDecimal amount, OPERATION operation){
        if(accountNumber == null || !(amount.doubleValue() > 0))return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        ClientAccount clientAccount =  clientAccountRepository.findById(accountNumber).orElse(null);
        if(clientAccount == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(statusMessageNotFound);
        BigDecimal currentBalance = clientAccount.getBalance();
        if(operation.equals(OPERATION.DEPOSIT))
           clientAccount.setBalance(currentBalance.add(amount));
        else if(operation.equals(OPERATION.WITHDRAWAL)){
            if(currentBalance.doubleValue() < amount.doubleValue())
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(statusMessageInsufficient);
        }
        ClientAccountStatement clientAccountStatement =  setClientAccountStatement(clientAccount, amount, operation);
        clientAccountStatementRepository.save(clientAccountStatement);
        clientAccountRepository.save(clientAccount);
        return ResponseEntity.ok(statusMessageSuccess);
    }
    @Override
    public ResponseEntity<List<ClientAccountStatement>> accountStatement(String accountNumber, OPERATION operation, Date date, BigDecimal amount, BigDecimal balance) {
        List<ClientAccountStatement> clientAccountStatements = clientAccountStatementRepository.findClientAccountStatementsBy(accountNumber, operation, date, amount, balance);
        if(clientAccountStatements.isEmpty())return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        else return ResponseEntity.ok(clientAccountStatements);
    }

    @Override
    public ResponseEntity<List<ClientAccountStatement>> accountStatement(OPERATION operation, Date date, BigDecimal amount, BigDecimal balance) {
        return accountStatement(null, operation, date, amount, balance);
    }

    private ClientAccountStatement setClientAccountStatement(ClientAccount clientAccount, BigDecimal transactionAmount, OPERATION operation){
        ClientAccountStatement clientAccountStatement = new ClientAccountStatement();
        clientAccountStatement.setAccountNumber(clientAccount.getAccountNumber());
        clientAccountStatement.setBalance(clientAccount.getBalance());
        clientAccountStatement.setTransactionAmount(transactionAmount);
        clientAccountStatement.setOperation(operation);
        return clientAccountStatement;
    }
}
