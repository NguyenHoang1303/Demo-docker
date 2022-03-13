package com.example.inventoryservice.queue;


import event.OrderEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.example.inventoryservice.queue.Config.QUEUE_INVENTORY;

@Component
@Log4j2
public class ReceiveMessage {

    @Autowired
    ConsumerService consumerService;


    @RabbitListener(queues = {QUEUE_INVENTORY})
    public void getInfoOrder(OrderEvent orderEvent) {
       consumerService.handlerInventory(orderEvent);
    }


}
