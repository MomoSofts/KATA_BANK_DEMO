package com.demo.bank.beans;

import com.demo.bank.beans.enums.CATEGORY;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "client_account")
public class ClientAccount {
    @Id
    @Column(name = "account_number")
    private String accountNumber;
    private String name;
    private String surname;
    private BigDecimal balance;
    private CATEGORY category;
}
