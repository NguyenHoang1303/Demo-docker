package com.example.paymentservice.controller;

import com.example.paymentservice.entity.TransactionHistory;
import com.example.paymentservice.entity.Wallet;
import com.example.paymentservice.repo.TransactionRepo;
import com.example.paymentservice.repo.WalletRepo;
import com.example.paymentservice.response.RESTResponse;
import com.example.paymentservice.service.WalletServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/payments/")
public class WalletController {

    @Autowired
    WalletRepo walletRepo;

    @Autowired
    TransactionRepo transactionRepo;

    @Autowired
    WalletServiceImpl walletService;

    @RequestMapping(path = "account/{userId}", method = RequestMethod.GET)
    public ResponseEntity find(@PathVariable int userId) {
        userId = 451691;
        Wallet wallet = walletRepo.findBalletByUserId((long) userId);
        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(wallet)
                .build(), HttpStatus.OK);
    }

    @RequestMapping(path = "transfer", method = RequestMethod.POST)
    public ResponseEntity send(@RequestBody TransactionHistory history) {
        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(walletService.transfer(history))
                .build(), HttpStatus.OK);
    }

    @RequestMapping(path = "account/transaction", method = RequestMethod.GET)
    public ResponseEntity getTransaction(@RequestParam(name = "userId") int userId) {
        userId = 451691;
        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(transactionRepo.findTransactionHistoryBySenderId((long) userId))
                .build(), HttpStatus.OK);
    }



}
