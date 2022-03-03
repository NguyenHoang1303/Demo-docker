package com.example.accountservice.controller;

import com.example.accountservice.service.AccountServiceInterface;
import com.example.accountservice.service.KeycloakAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("accounts")
public class AccountController {

    @Bean
    AccountServiceInterface accountServiceInterface() {
        return new KeycloakAccountService();
    }

    @Autowired
    AccountServiceInterface accountServiceInterface;

    @RequestMapping(method = RequestMethod.GET)
    @RolesAllowed("user")
    public String getList() {
        return "List Order";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/detail")
    public String getDetail() {
        return "Get Order Detail";
    }
}
