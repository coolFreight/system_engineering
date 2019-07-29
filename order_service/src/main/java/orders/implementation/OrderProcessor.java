package orders.implementation;

import orders.dao.OrderServiceDao;
import orders.messaging.OrderServicePublisher;
import orders.model.Order;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

public class OrderProcessor {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(OrderProcessor.class);

    private OrderServicePublisher publisher;
    private OrderServiceDao orderServiceDao;

    public void setOrderPublisher(OrderServicePublisher publisher) {
        this.publisher = publisher;
    }

    public void setOrderServiceDao(OrderServiceDao orderServiceDao) {
        this.orderServiceDao = orderServiceDao;
    }

    public void startOrderProcessingService() {
        LOGGER.info("Polling for new orders created");
        ScheduledExecutorService threadPool = Executors.newSingleThreadScheduledExecutor();
        threadPool.scheduleAtFixedRate(() -> publishPendingOrders(), 1L, 5L, TimeUnit.SECONDS);
    }

    private void publishPendingOrders() {
        List<Integer> orderIds = orderServiceDao.getOutBoxUnpublishedOrders();
        if (!orderIds.isEmpty()) {
            List<Order> orders = orderServiceDao.getOrders(orderIds);
            for (Order order : orders) {
                publisher.publishOrderEvent(order);
                orderServiceDao.updateOrderOutboxStatus(order.getOrderId());
                LOGGER.info("Order {} was published", order.getOrderId());
            }
        }
    }
}
