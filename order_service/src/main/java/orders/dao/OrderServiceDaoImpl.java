package orders.dao;

import orders.model.Order;
import orders.model.OrderStatus;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderServiceDaoImpl implements OrderServiceDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceDaoImpl.class);
    public static final String ORDER_ID = "order_id";
    public static final String ORDER_STATUS = "order_status";

    private PreparedStatement preparedStatement = null;
    private static final String DB_NAME = "orders";
    private String UNPUBLISHED_ORDER_IDS = "select * from " + DB_NAME + ".order_outbox where published = 'f' ";
    private String updateOrderPublished = "update  " + DB_NAME + ".order_outbox set published = 't' where order_id= ?";
    private String ORDERS = "select * from " + DB_NAME + ".orders where order_id in ( ? ) ";
    private static BasicDataSource ds = new BasicDataSource();

    static {
        ds.setUrl("jdbc:mysql://localhost:3306/?serverTimezone=UTC");
        ds.setUsername("serialcoderer");
        ds.setPassword("rainbow14");
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }

    @Override
    public Order createOrder(Order order) {
        return null;
    }


    @Override
    public List<Integer> getOutBoxUnpublishedOrders() {
        LOGGER.info("Querying for unpublished outbox orders");
        List<Integer> orderIds = new ArrayList<>();
        try (Connection conn = ds.getConnection()){
            preparedStatement = conn.prepareStatement(UNPUBLISHED_ORDER_IDS);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                orderIds.add(rs.getInt(ORDER_ID));
            }
        } catch (SQLException e) {
            LOGGER.error("Could not retrieve unpublished orders", e);
            throw new RuntimeException(e.getMessage());
        }
        LOGGER.info("There are {} unpublished orders", orderIds.size());
        return orderIds;
    }

    @Override
    public boolean updateOrderOutboxStatus(int orderId) {
        LOGGER.info("Updating order status of order {} ", orderId);
        boolean publishedStatus = false;
        try (Connection conn = ds.getConnection()) {
            preparedStatement = conn.prepareStatement(updateOrderPublished);
            preparedStatement.setInt(1, orderId);
            publishedStatus = preparedStatement.execute();
            LOGGER.info("Successfully updated order outbox status for order id {}", orderId);
        } catch (SQLException e) {
            LOGGER.error("Could not mark order id {} published", orderId, e);
        } finally {
            return publishedStatus;
        }
    }

    @Override
    public List<Order> getOrders(List<Integer> orderIds) {
        LOGGER.info("Querying for {} orders ", orderIds.size());
        List<Order> orders = new ArrayList<>();
        try (Connection conn = ds.getConnection()) {
            List<String> params = orderIds.stream().map(d -> d.toString()).collect(Collectors.toList());
            String joinedParams = String.join(",", params);
            String getOrdersSql = ORDERS.replace("?", joinedParams);
            LOGGER.info("Constructed getOrderSql= {}", getOrdersSql);
            preparedStatement = conn.prepareStatement(getOrdersSql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt(1));
                order.setOrderStatus(OrderStatus.valueOf(rs.getString(2)));
                orders.add(order);
            }
        } catch (SQLException e) {
            LOGGER.error("Could not get orders for ids {}", orderIds);
        }
        LOGGER.info("Retrieved {} orders from the database ", orders.size());
        return orders;
    }
}
