package com.xische.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Item {
    private String name;
    private BigDecimal price;
    private boolean isGrocery;
}
