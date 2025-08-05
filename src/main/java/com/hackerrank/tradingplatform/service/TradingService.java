package com.hackerrank.tradingplatform.service;

import com.hackerrank.tradingplatform.model.Trader;
import com.hackerrank.tradingplatform.repository.TraderRepository;
import org.springframework.stereotype.Service;
import com.hackerrank.tradingplatform.exception.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class TradingService {
    private final TraderRepository traderRepository;

    public TradingService(TraderRepository traderRepository) {
        this.traderRepository = traderRepository;
    }

    public Trader registerTrader(Trader trader) {
        if (traderRepository.findByEmail(trader.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Trader with this email already exists");
        }

        trader.setId(null);
        trader.setCreatedAt(Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC)));
        trader.setUpdatedAt(null);
        trader.setBalance(trader.getBalance() != null ? trader.getBalance() : 0.0);

        return traderRepository.save(trader);
    }

    public List<Trader> getAllTraders() {
        return traderRepository.findAllOrderByIdAsc();
    }


     public List<Trader> getAll() {
         return traderRepository.findAllOrderByIdDesc();
     }

    public Trader getTraderByEmail(String email) {
        return traderRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Trader not found"));
    }

    public Trader updateTraderName(String email, String name) {
        Trader trader = getTraderByEmail(email);
        trader.setName(name);
        trader.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC)));
        return traderRepository.save(trader);
    }

    public Trader addMoneyToTrader(String email, Double amount) {
        Trader trader = getTraderByEmail(email);
        trader.setBalance(trader.getBalance() + amount);
        trader.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC)));
        return traderRepository.save(trader);
    }
}