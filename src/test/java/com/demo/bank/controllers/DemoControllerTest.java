package com.demo.bank.controllers;

import com.demo.bank.beans.ClientAccountStatement;
import com.demo.bank.beans.enums.OPERATION;
import com.demo.bank.services.IService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DemoController.class)
class DemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IService mockService;

    @Test
    void testDeposit() throws Exception {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.OK)).when(mockService).deposit("accountNumber",
                new BigDecimal("0.00"));

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                        post("/deposit/to/{accountNumber}/amount/{amount}", "accountNumber", 0)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("");
    }

    @Test
    void testWithdraw() throws Exception {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.OK)).when(mockService).withdraw("accountNumber",
                new BigDecimal("0.00"));

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                        post("/withdraw/from/{accountNumber}/amount/{amount}", "accountNumber", 0)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("");
    }

}
