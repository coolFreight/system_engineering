package orders.implementation;

import orders.model.Order;
import orders.model.OrderItem;

import java.util.Collection;

public interface OrderService {

    Order createOrder(Collection<OrderItem> orderItems);

}
