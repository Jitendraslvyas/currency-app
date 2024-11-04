package com.xische.model.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class ExchangeRateResponse {
    private String result;
    private String provider;
    private String documentation;
    private String termsOfUse;
    private long timeLastUpdateUnix;
    private String timeLastUpdateUtc;
    private long timeNextUpdateUnix;
    private String timeNextUpdateUtc;
    private long timeEolUnix;
    private String baseCode;
    private Map<String, BigDecimal> rates;
}
