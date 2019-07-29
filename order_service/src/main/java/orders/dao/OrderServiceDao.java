package orders.dao;

import orders.model.Order;

import java.math.BigDecimal;
import java.util.List;

public interface OrderServiceDao {

    Order createOrder(Order order);

    List<Integer> getOutBoxUnpublishedOrders();

    List<Order> getOrders(List<Integer> orderIds);

    boolean updateOrderOutboxStatus(int orderId);

}
