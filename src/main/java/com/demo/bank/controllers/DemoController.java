package com.demo.bank.controllers;

import com.demo.bank.beans.ClientAccount;
import com.demo.bank.beans.ClientAccountStatement;
import com.demo.bank.beans.enums.CATEGORY;
import com.demo.bank.beans.enums.OPERATION;
import com.demo.bank.services.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
public class DemoController  {

    private final IService service;

    @Autowired
    public DemoController(IService service) {
        this.service = service;
        List<ClientAccount> clientAccounts = List.of(
                generateClientAccount("00125347807","Abacus", "Balla", new BigDecimal(10000.0), CATEGORY.CREDIT),
                generateClientAccount("00125333807","Balac", "Acus", new BigDecimal(20000.0), CATEGORY.CURRENT),
                generateClientAccount("00125355407","Aboua", "Aumgbe", new BigDecimal(30000.0), CATEGORY.CREDIT),
                generateClientAccount("00125388807","Camara", "Isham", new BigDecimal(40000.0), CATEGORY.SAVING)
        );
        feedPrimaryData(clientAccounts);
    }

    public void feedPrimaryData(List<ClientAccount> accounts) {
       service.feedPrimaryData(accounts);
    }

    @PostMapping("/deposit/to/{accountNumber}/amount/{amount}")
    public ResponseEntity<?> deposit(@Size(min = 11, max = 11) @PathVariable String accountNumber,@PathVariable BigDecimal amount) {
        return service.deposit(accountNumber, amount);
    }

    @PostMapping("/withdraw/from/{accountNumber}/amount/{amount}")
    public ResponseEntity<?> withdraw(@Size(min = 11, max = 11) @PathVariable String accountNumber, @PathVariable BigDecimal amount) {
        return service.withdraw(accountNumber, amount);
    }

    @GetMapping("/account-statement/{accountNumber}")
    public ResponseEntity<List<ClientAccountStatement>> accountStatement(@Size(min = 8, max = 11) @PathVariable String accountNumber,
                                                                         @RequestParam(required = false) OPERATION operation,
                                                                         @RequestParam(required = false) Date date,
                                                                         @RequestParam(required = false) BigDecimal amount,
                                                                         @RequestParam(required = false) BigDecimal balance) {
        return service.accountStatement(accountNumber,operation, date, amount, balance);
    }
    @GetMapping("/account-statement")
    public ResponseEntity<List<ClientAccountStatement>> accountStatement(@RequestParam(required = false) OPERATION operation, @RequestParam(required = false) Date date,
                                                                         @RequestParam(required = false) BigDecimal amount, @RequestParam(required = false) BigDecimal balance) {
        return service.accountStatement(operation, date, amount, balance);
    }

    private ClientAccount generateClientAccount(String clientAccountNumber, String name, String surname, BigDecimal balance, CATEGORY category){
        ClientAccount clientAccount = new ClientAccount();
        clientAccount.setAccountNumber(clientAccountNumber);
        clientAccount.setName(name);
        clientAccount.setSurname(surname);
        clientAccount.setBalance(balance);
        clientAccount.setCategory(category);
        return clientAccount;
    }
}
