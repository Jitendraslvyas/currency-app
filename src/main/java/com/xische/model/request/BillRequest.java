package com.xische.model.request;

import com.xische.model.Item;
import com.xische.model.UserType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BillRequest {
    private List<Item> items;
    private UserType userType;
    private int customerTenure;
    private BigDecimal totalAmount;
    private String originalCurrency;
    private String targetCurrency;
}
