package com.xische.service;

import com.xische.model.request.BillRequest;
import com.xische.util.DiscountCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CalculationService {

    @Autowired
    private DiscountCalculator discountCalculator;

    @Autowired
    private ExchangeService exchangeService;

    public BigDecimal calculateFinalAmount(BillRequest request) {
        // Step 1: Calculate discounted amount
        BigDecimal discountedAmount = discountCalculator.calculateDiscountedAmount(
                request.getUserType(), request.getItems(), request.getTotalAmount());
        
        // Step 2: Retrieve exchange rate
        BigDecimal exchangeRate = exchangeService.getExchangeRate(
                request.getOriginalCurrency(), request.getTargetCurrency());
        
        // Step 3: Convert to target currency
        return convertToTargetCurrency(discountedAmount, exchangeRate, request.getTargetCurrency());
    }

    private BigDecimal convertToTargetCurrency(BigDecimal amount, BigDecimal exchangeRate, String targetCurrency) {
        return amount.multiply(exchangeRate);
    }
}
