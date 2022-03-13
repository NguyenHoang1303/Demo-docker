package com.example.paymentservice.queue;

import com.example.paymentservice.entity.TransactionHistory;
import com.example.paymentservice.entity.Wallet;
import com.example.paymentservice.enums.PaymentStatus;
import com.example.paymentservice.enums.PaymentType;
import com.example.paymentservice.enums.TransactionStatus;
import com.example.paymentservice.repo.TransactionRepo;
import com.example.paymentservice.service.WalletService;
import com.example.paymentservice.translate.TranslationService;
import event.OrderEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.example.paymentservice.constant.KeyI18n.*;
import static com.example.paymentservice.queue.Config.*;

@Component
@Log4j2
public class ConsumerService {

    @Autowired
    WalletService walletService;

    @Autowired
    TransactionRepo transactionRepo;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    TranslationService translationService;

    @Transactional
    public void handlerPayment(OrderEvent orderEvent) {
        orderEvent.setQueueName(QUEUE_PAY);
        if (!orderEvent.validationPayment()) {
            orderEvent.setMessage(translationService.translate(CHECK_INFO_PAYMENT));
            rabbitTemplate.convertAndSend(DIRECT_EXCHANGE, DIRECT_ROUTING_KEY_ORDER, orderEvent);
            return;
        }
        if (orderEvent.getPaymentStatus().equals(PaymentStatus.REFUND.name())) {
            handlerOrderRefund(orderEvent);
            return;
        }
        if (orderEvent.getPaymentStatus().equals(PaymentStatus.UNPAID.name())) {
            handlerOrderUnpaid(orderEvent);
        }
    }

    @Transactional
    void handlerOrderRefund(OrderEvent orderEvent) {
        Wallet wallet = checkWalletExist(orderEvent);
        if (wallet == null) return;
        TransactionHistory history = TransactionHistory.Builder.aTransactionHistory()
                .withReceiverId(orderEvent.getUserId())
                .withSenderId(1L)
                .withOrderId(orderEvent.getOrderId())
                .withAmount(orderEvent.getTotalPrice())
                .withPaymentType(PaymentType.REFUND.name())
                .build();

        try {
            wallet.setBalance(wallet.getBalance().add(orderEvent.getTotalPrice()));
            history.setStatus(TransactionStatus.SUCCESS.name());
            history.setMessage(translationService.translate(REFUND_SUCCESS));
            orderEvent.setPaymentStatus(PaymentStatus.REFUNDED.name());
            walletService.save(wallet);
            transactionRepo.save(history);

            rabbitTemplate.convertAndSend(DIRECT_EXCHANGE, DIRECT_ROUTING_KEY_ORDER, orderEvent);
        } catch (Exception e) {
            history.setStatus(TransactionStatus.FAIL.name());
            transactionRepo.save(history);
            rabbitTemplate.convertAndSend(DIRECT_EXCHANGE, DIRECT_ROUTING_KEY_PAY, orderEvent);
            throw new RuntimeException(e.getMessage());
        }
    }

    void handlerOrderUnpaid(OrderEvent orderEvent) {
        Wallet wallet = checkWalletExist(orderEvent);
        if (wallet == null) return;

        BigDecimal totalPrice = orderEvent.getTotalPrice();
        BigDecimal balance = wallet.getBalance();

        if (totalPrice.compareTo(balance) > 0) {
            orderEvent.setMessage(translationService.translate(NOT_ENOUGH_BALANCE));
            orderEvent.setPaymentStatus(PaymentStatus.FAIL.name());
            rabbitTemplate.convertAndSend(DIRECT_EXCHANGE, DIRECT_ROUTING_KEY_ORDER, orderEvent);
            return;
        }
        TransactionHistory history = TransactionHistory.Builder
                .aTransactionHistory()
                .withSenderId(orderEvent.getUserId())
                .withReceiverId(1l)
                .withOrderId(orderEvent.getOrderId())
                .withAmount(orderEvent.getTotalPrice())
                .withPaymentType(PaymentType.SENDING.name())
                .build();

        try {
            wallet.setBalance(balance.subtract(totalPrice));
            history.setStatus(TransactionStatus.SUCCESS.name());
            history.setMessage(translationService.translate(PAID));
            orderEvent.setPaymentStatus(PaymentStatus.PAID.name());
            orderEvent.setMessage(translationService.translate(PAID));
            walletService.save(wallet);
            transactionRepo.save(history);
            rabbitTemplate.convertAndSend(DIRECT_EXCHANGE, DIRECT_ROUTING_KEY_ORDER, orderEvent);
        } catch (Exception e) {
            history.setStatus(TransactionStatus.FAIL.name());
            transactionRepo.save(history);
            rabbitTemplate.convertAndSend(DIRECT_EXCHANGE, DIRECT_ROUTING_KEY_PAY, orderEvent);
            throw new RuntimeException(e.getMessage());
        }
    }

    private Wallet checkWalletExist(OrderEvent orderEvent) {
        Wallet wallet = walletService.findBalletByUserId(orderEvent.getUserId());
        if (wallet == null) {
            orderEvent.setMessage(translationService.translate(USER_NOTFOUND));
            orderEvent.setPaymentStatus(PaymentStatus.FAIL.name());
            rabbitTemplate.convertAndSend(DIRECT_EXCHANGE, DIRECT_ROUTING_KEY_ORDER, orderEvent);
            return null;
        }
        return wallet;
    }

}
