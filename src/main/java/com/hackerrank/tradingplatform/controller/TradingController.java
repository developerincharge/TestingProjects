package com.hackerrank.tradingplatform.controller;

import com.hackerrank.tradingplatform.model.Trader;
import com.hackerrank.tradingplatform.service.TradingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.hackerrank.tradingplatform.exception.*;

import java.util.*;

@RestController
@RequestMapping("/trading/traders")
public class TradingController {
    private final TradingService tradingService;

    public TradingController(TradingService tradingService) {
        this.tradingService = tradingService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Trader registerTrader(@RequestBody Trader trader) {
        return tradingService.registerTrader(trader);
    }

    @GetMapping("/all")
    public List<Trader> getAllTraders() {
        return tradingService.getAllTraders();
    }

    @GetMapping
    public Trader getTraderByEmail(@RequestParam String email) {
        return tradingService.getTraderByEmail(email);
    }

    @PutMapping
    public Trader updateTraderName(@RequestBody Trader trader) {
        return tradingService.updateTraderName(trader.getEmail(), trader.getName());
    }

    @PutMapping("/add")
    public Trader addMoneyToTrader(@RequestBody Map<String, Object> payload) {
        String email = (String) payload.get("email");
        Double amount = Double.valueOf(payload.get("amount").toString());
        return tradingService.addMoneyToTrader(email, amount);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgument(IllegalArgumentException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(NotFoundException ex) {
        return ex.getMessage();
    }
}