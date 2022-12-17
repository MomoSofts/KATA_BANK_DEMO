package com.demo.bank.beans;

import com.demo.bank.beans.enums.OPERATION;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
public class ClientAccountStatement {
    @Id
    @Column(name = "account_number")
    private String accountNumber;
    @Column(name = "transaction_amount")
    private BigDecimal transactionAmount;
    private BigDecimal balance;

    private OPERATION operation;

    private final Date date = new Date();

}
