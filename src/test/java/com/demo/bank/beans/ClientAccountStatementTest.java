package com.demo.bank.beans;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;

class ClientAccountStatementTest {

    private ClientAccountStatement clientAccountStatementUnderTest;

    @BeforeEach
    void setUp() {
        clientAccountStatementUnderTest = new ClientAccountStatement();
    }

  //  @Test
   // void testEquals() {
//        assertThat(clientAccountStatementUnderTest.equals(new ClientAccountStatement())).isTrue();
   // }

 //   @Test
 //   void testHashCode() {
//        assertThat(clientAccountStatementUnderTest.hashCode() == new ClientAccountStatement().hashCode()).isTrue();
  //  }


}
