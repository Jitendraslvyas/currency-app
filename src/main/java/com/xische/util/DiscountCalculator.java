package com.xische.util;

import com.xische.model.Item;
import com.xische.model.UserType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DiscountCalculator {

    public BigDecimal calculateDiscountedAmount(UserType userType, List<Item> items, BigDecimal totalAmount) {
        BigDecimal nonGroceryTotal = calculateNonGroceryTotal(items);
        BigDecimal flatDiscount = calculateFlatDiscount(totalAmount);
        
        BigDecimal percentageDiscount = calculatePercentageDiscount(userType, nonGroceryTotal);
        
        // Apply the percentage discount to non-grocery items only
        BigDecimal totalAfterPercentageDiscount = nonGroceryTotal.subtract(percentageDiscount).add(calculateGroceryTotal(items));
        
        // Subtract the flat discount from the overall total
        return totalAfterPercentageDiscount.subtract(flatDiscount);
    }
    
    private BigDecimal calculateNonGroceryTotal(List<Item> items) {
        return items.stream()
                .filter(item -> !item.isGrocery())
                .map(Item::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    private BigDecimal calculateGroceryTotal(List<Item> items) {
        return items.stream()
                .filter(Item::isGrocery)
                .map(Item::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    private BigDecimal calculateFlatDiscount(BigDecimal totalAmount) {
        return totalAmount.divide(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_DOWN).multiply(new BigDecimal(5));
    }
    
    private BigDecimal calculatePercentageDiscount(UserType userType, BigDecimal nonGroceryTotal) {
        BigDecimal discountRate = BigDecimal.ZERO;

        // Apply discounts based on user type
        if (userType == UserType.EMPLOYEE) {
            discountRate = new BigDecimal("0.30"); // 30% discount
        } else if (userType == UserType.AFFILIATE) {
            discountRate = new BigDecimal("0.10"); // 10% discount
        } else if (userType == UserType.LOYAL_CUSTOMER) {
            discountRate = new BigDecimal("0.05"); // 5% discount
        }
        return nonGroceryTotal.multiply(discountRate);
    }
}
