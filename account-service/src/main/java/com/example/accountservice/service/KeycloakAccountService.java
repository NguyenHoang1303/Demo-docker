package com.example.accountservice.service;


import com.example.accountservice.entity.Account;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KeycloakAccountService implements AccountServiceInterface {

    @Override
    public List<Account> findAll(){
        /**
         * dùng jsoup get|post lên api của keycloak
         * lấy dữ liệu và parse ra thành danh sách của lớp Account, return về
         * một list account
         */
        return new ArrayList<Account>();
    }

    @Override
    public Account findById(String id) {
        return null;
    }

    @Override
    public Account save(Account account) {
        return null;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}
