package com.example.authenticationservice.service;

import com.example.authenticationservice.Dto.AccountDto;
import com.example.authenticationservice.entity.Account;
import com.example.authenticationservice.entity.Credential;
import com.example.authenticationservice.entity.RoleKeyCloak;

import java.io.IOException;
import java.util.List;

public interface AccountService {
    List<Account> findAll() throws IOException;
    AccountDto findById(String id);
    boolean addRoleToUser(String id, RoleKeyCloak roles);
    AccountDto save(Account account);
    boolean update(String id ,Account account);
    boolean delete(String id);
    Credential login(String username,String password);
    AccountDto getProfile();
}
