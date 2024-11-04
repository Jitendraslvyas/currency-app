package com.xische.controller;

import com.xische.model.request.BillRequest;
import com.xische.model.response.BillResponse;
import com.xische.service.CalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * Exposed an API endpoint that allows users to submit a bill in one currency and get the
 * payable amount in another currency.
 *
 * @author Jitendra Vyas
 * @version 1.0
 */
@RestController
@RequestMapping("/api")
public class CalculationController {

    @Autowired
    private CalculationService calculationService;

    @PostMapping("/calculate")
    public ResponseEntity<BillResponse> calculateBill(@RequestBody BillRequest request) {
        BigDecimal payableAmount = calculationService.calculateFinalAmount(request);
        return ResponseEntity.ok(new BillResponse(payableAmount, request.getTargetCurrency()));
    }
}
