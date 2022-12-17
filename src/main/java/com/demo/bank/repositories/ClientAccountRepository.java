package com.demo.bank.repositories;

import com.demo.bank.beans.ClientAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientAccountRepository extends JpaRepository<ClientAccount, String> {}
