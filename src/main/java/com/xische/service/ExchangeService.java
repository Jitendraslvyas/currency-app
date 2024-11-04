package com.xische.service;

import com.xische.exception.InvalidCurrencyException;
import com.xische.model.response.ExchangeRateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Slf4j
public class ExchangeService {

    @Value("${exchange.api}")
    private String exchangeApi;

    @Value("${exchange.api.key}")
    private String exchangApiKey;

    @Autowired
    private RestTemplate restTemplate;

    public BigDecimal getExchangeRate(String baseCurrency, String targetCurrency) {
        String url = String.format(exchangeApi, baseCurrency, exchangApiKey);
        log.info("Exchange url: {}", url);
        ExchangeRateResponse exchangeRateResponse = restTemplate.getForObject(url, ExchangeRateResponse.class);
        return Optional.ofNullable(exchangeRateResponse.getRates().get(targetCurrency))
                .orElseThrow(() -> new InvalidCurrencyException("Invalid target currency: " + targetCurrency));
    }
}
