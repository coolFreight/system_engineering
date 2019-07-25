package orders.model;

import java.util.ArrayList;
import java.util.Collection;

public class Order {
    private OrderStatus orderStatus;
    private Collection<OrderItem> orderItems = new ArrayList<>();

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }

    public Collection<OrderItem> getOrderItems() {
        return orderItems;
    }

}
