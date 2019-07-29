package orders.implementation;

import orders.model.Order;
import orders.model.OrderItem;

import java.util.Collection;

public class OrderServiceImpl implements OrderService {

    private OrderService orderService;

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public Order createOrder(Order order) {
        return orderService.createOrder(order);
    }
}
