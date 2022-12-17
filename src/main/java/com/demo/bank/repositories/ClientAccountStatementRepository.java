package com.demo.bank.repositories;

import com.demo.bank.beans.ClientAccountStatement;
import com.demo.bank.beans.enums.OPERATION;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ClientAccountStatementRepository extends JpaRepository<ClientAccountStatement, String> {

    @Query("select c from ClientAccountStatement  c where (c.accountNumber = :accountNumber OR c.accountNumber IS NULL)" +
            "and (c.operation IS NULL or c.operation = :operation) " +
            "and (c.date IS NULL or c.date = :date)" +
            "and (c.transactionAmount IS NULL or c.transactionAmount = :amount) " +
            "and (c.balance IS NULL or c.balance = :balance) ")
    public List<ClientAccountStatement> findClientAccountStatementsBy(@Param("accountNumber") String accountNumber, @Param("operation") OPERATION operation,
                                                                      @Param("date") Date date, @Param("amount") BigDecimal amount, @Param("balance") BigDecimal balance);
}
