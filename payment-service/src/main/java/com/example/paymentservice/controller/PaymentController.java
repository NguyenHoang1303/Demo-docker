package com.example.paymentservice.controller;

import com.example.paymentservice.entity.Wallet;
import com.example.paymentservice.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("payments")
public class PaymentController {

    @Autowired
    WalletRepository repository;

    @RequestMapping
    public List<Wallet> getAll(){
        return repository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Wallet save(@RequestBody Wallet wallet){
        return repository.save(wallet);
    }
}
