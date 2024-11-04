package com.xische.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xische.model.Item;
import com.xische.model.UserType;
import com.xische.model.request.BillRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CalculationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCalculatePayableAmount_Success() throws Exception {

        // Arrange: Create the request payload
        BillRequest request = new BillRequest();
        request.setItems(Arrays.asList(
                new Item("Product 1", BigDecimal.valueOf(50.00), false),
                new Item("Product 2", BigDecimal.valueOf(30.00), true)
        ));
        request.setUserType(UserType.LOYAL_CUSTOMER);
        request.setCustomerTenure(12);
        request.setTotalAmount(BigDecimal.valueOf(20.00));
        request.setOriginalCurrency("USD");
        request.setTargetCurrency("EUR");

        // Act & Assert: Perform the request and verify the response
        mockMvc.perform(post("/api/calculate")
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.targetCurrency").value("EUR"));
    }

    @Test
    void testCalculatePayableAmount_InvalidCurrencyException() throws Exception {

        // Arrange: Create the request payload
        BillRequest request = new BillRequest();
        request.setItems(Arrays.asList(
                new Item("Product 1", BigDecimal.valueOf(50.00), false),
                new Item("Product 2", BigDecimal.valueOf(30.00), true)
        ));
        request.setUserType(UserType.LOYAL_CUSTOMER);
        request.setCustomerTenure(12);
        request.setTotalAmount(BigDecimal.valueOf(20.00));
        request.setOriginalCurrency("USD");
        request.setTargetCurrency("TEST");

        // Act & Assert: Perform the request and verify the response
        mockMvc.perform(post("/api/calculate")
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Invalid target currency")));
    }

}
