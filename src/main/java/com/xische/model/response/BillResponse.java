package com.xische.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class BillResponse {
    private BigDecimal payableAmount;
    private String targetCurrency;
}
