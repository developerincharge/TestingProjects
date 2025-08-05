package com.hackerrank.tradingplatform.controller;

import com.hackerrank.tradingplatform.dto.AddMoneyTraderDTO;
import com.hackerrank.tradingplatform.dto.RegistrationDTO;
import com.hackerrank.tradingplatform.dto.UpdateTraderDTO;
import com.hackerrank.tradingplatform.model.Trader;
import com.hackerrank.tradingplatform.service.TraderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "/trading/traders")
public class TraderController {
    @Autowired
    private TraderService traderService;
    private RegistrationDTO registrationDTO;

    //register
    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> registerTrader(@RequestBody @Valid Trader trader) {

        if (traderService.getTraderByEmail(trader.getEmail()).getId() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
        }

        traderService.registerTrader(trader);

        return ResponseEntity.ok("Trader registered successfully"+ registrationDTO.getName());
    }



    //get by email
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public RegistrationDTO getTraderByEmail(@RequestParam("email") String email) {
        return new RegistrationDTO(traderService.getTraderByEmail(email));
    }

    //get all
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<RegistrationDTO> getAllTraders() {
        return traderService.getAllTraders()
                .stream()
                .map(RegistrationDTO::new)
                .collect(toList());
    }

    //update by email
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateTrader(@RequestBody @Valid UpdateTraderDTO trader) {
        traderService.updateTrader(trader);
    }

    //add money
    @RequestMapping(value = "/add", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void addMoney(@RequestBody @Valid AddMoneyTraderDTO trader) {
        traderService.addMoney(trader);
    }
}
