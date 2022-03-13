package com.example.orderservice.service;


import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.entity.CartItem;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderDetail;
import com.example.orderservice.enums.InventoryStatus;
import com.example.orderservice.enums.OrderStatus;
import com.example.orderservice.enums.PaymentStatus;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.translate.TranslationService;
import event.OrderEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.orderservice.constant.KeyI18n.ORDER_NOT_PRODUCT;
import static com.example.orderservice.queue.Config.DIRECT_EXCHANGE;
import static com.example.orderservice.queue.Config.DIRECT_SHARE_ROUTING_KEY;


@Service
@Log4j2
public class OrderServiceImpl implements OrderService {


    @Autowired
    CartService cartService;

    @Autowired
    OrderRepository orderRepo;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    TranslationService translationService;

    @Override
    @Transactional
    public OrderDto create(Order order) {
        Order orderSave;
        try {
            orderSave = orderRepo.save(order);
            BigDecimal totalPrice = BigDecimal.valueOf(0);
            Set<OrderDetail> orderDetailHashSet = new HashSet<>();
            for (CartItem cartItem : CartServiceImpl.cartHashMap.values()) {
                totalPrice = totalPrice.add(cartItem.getUnitPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
                OrderDetail orderDetail = new OrderDetail(cartItem);
                orderDetail.setOrderId(orderSave.getId());
                orderDetailHashSet.add(orderDetail);
                log.info("cartItem: " + cartItem);
            }

            if (totalPrice.compareTo(BigDecimal.valueOf(0)) <= 0) {
                throw new RuntimeException(translationService.translate(ORDER_NOT_PRODUCT));
            }
            log.info("totalPrice: " + totalPrice);

            orderSave.setTotalPrice(totalPrice);
            orderSave.setCreatedAt(LocalDate.now());
            orderSave.setPaymentStatus(PaymentStatus.UNPAID.name());
            orderSave.setOrderStatus(OrderStatus.PENDING.name());
            orderSave.setInventoryStatus(InventoryStatus.PENDING.name());
            orderSave.setOrderDetails(orderDetailHashSet);

            rabbitTemplate.convertAndSend(DIRECT_EXCHANGE, DIRECT_SHARE_ROUTING_KEY, new OrderEvent(orderSave));
            cartService.clear();
            return new OrderDto(orderSave);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Page<Order> getAll(int page, int pageSize) {
        if (page <= 0) {
            page = 1;
        }
        if (pageSize < 0) {
            page = 6;
        }
        return orderRepo.findAll(PageRequest.of(page - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Order findById(Long orderId) {
        return orderRepo.findById(orderId).orElse(null);
    }

    @Override
    public Order save(Order orderExist) {
        return orderRepo.save(orderExist);
    }

    @Override
    public List findOrderByUserId(long userId) {
        return orderRepo.findOrderByUserId(userId);
    }


}
