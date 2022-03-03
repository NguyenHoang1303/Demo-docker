package com.example.accountservice.service;

import com.example.accountservice.entity.Account;

import java.util.List;

public interface AccountServiceInterface {
    List<Account> findAll();
    Account findById(String id);
    Account save(Account account);
    boolean delete(String id);
}
