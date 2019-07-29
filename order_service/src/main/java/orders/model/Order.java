package orders.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class Order implements Serializable {

    private OrderStatus orderStatus;
    private int orderId;
    private String description;

    private Collection<OrderItem> orderItems = new ArrayList<>();

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }

    public Collection<OrderItem> getOrderItems() {
        return orderItems;
    }

    public int getOrderId(){
        return orderId;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOrderItems(Collection<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
