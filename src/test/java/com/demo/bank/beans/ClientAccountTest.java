package com.demo.bank.beans;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClientAccountTest {

    private ClientAccount clientAccountUnderTest;

    @BeforeEach
    void setUp() {
        clientAccountUnderTest = new ClientAccount();
    }

    @Test
    void testEquals() {
        assertThat(clientAccountUnderTest.equals(new ClientAccount())).isTrue();
    }

    @Test
    void testHashCode() {
        assertThat(clientAccountUnderTest.hashCode() == new ClientAccountStatement().hashCode()).isFalse();
    }

}
