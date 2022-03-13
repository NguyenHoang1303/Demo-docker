package com.example.paymentservice.repo;

import com.example.paymentservice.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepo extends JpaRepository<Wallet, Long> {
    Wallet findBalletByUserId(Long id);
}
