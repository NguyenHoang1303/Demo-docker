package event;


import com.example.orderservice.entity.Order;
import lombok.*;


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderEvent {

    private Long orderId;
    private Long userId;
    private Set<OrderDetailEvent> orderDetailEvents = new HashSet<>();
    private BigDecimal totalPrice;
    private String paymentStatus;
    private String inventoryStatus;
    private String orderStatus;
    private String device_token;
    private String message;
    private String queueName;

    public OrderEvent(Order order) {
        this.orderId = order.getId();
        this.userId = order.getUserId();
        this.totalPrice = order.getTotalPrice();
        this.paymentStatus = order.getPaymentStatus();
        this.inventoryStatus = order.getInventoryStatus();
        this.device_token = "yVv77AFu561i3UONuCqSV:APA91bGXkO2VjRvDmQm2wh45K4WTn18pJn2l6DXWMkUC8FTLHFgBVtbfBBzcYBzq_kXVkoTL8xm_mp3PPLG0hJUxDTIw_x6ZxGx7ShWHnaozyW8JpGQN-KHvip5Cb0P5qYiFj_Ap83rt";
        this.orderStatus = order.getOrderStatus();
        order.getOrderDetails().forEach(orderDetail -> {
            this.orderDetailEvents.add(new OrderDetailEvent(orderDetail));
        });
    }

    public boolean validationPayment() {
        return this.totalPrice.compareTo(BigDecimal.valueOf(0)) > 0
                && this.orderId != null && this.userId != null && this.paymentStatus != null;
    }

    public boolean validationInventory() {
        return this.orderId != null && this.orderStatus != null && this.inventoryStatus != null;
    }

}
