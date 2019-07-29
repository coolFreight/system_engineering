package orders;

import orders.dao.OrderServiceDao;
import orders.dao.OrderServiceDaoImpl;
import orders.implementation.OrderProcessor;
import orders.messaging.OrderServicePublisher;
import orders.model.Order;
import orders.model.OrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

public class OrderStoreFront {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderStoreFront.class);


    public static void main(String[] args) throws IOException {
        OrderServiceDao orderServiceDao = new OrderServiceDaoImpl();
        OrderServicePublisher publisher = new OrderServicePublisher();
        OrderProcessor orderProcessor = new OrderProcessor();
        orderProcessor.setOrderPublisher(publisher);
        orderProcessor.setOrderServiceDao(orderServiceDao);
        publisher.init();
        orderProcessor.startOrderProcessingService();
        if(args.length != 0){
            LOGGER.info("Running Order Service tester ...");
        }else {

//            Scanner sc = new Scanner(System.in);
//            do {
//                LOGGER.info("Waiting for input");
//                int command = sc.nextInt();
//                LOGGER.info("Received input {} ", command);
//                switch (command) {
//                    case 1:
//                        LOGGER.info("Enter order description ...");
//                        String orderDescription = sc.next();
//                        Order o = new Order();
//                        OrderItem oi = new OrderItem();
//                        oi.setDescription(orderDescription);
//                        oi.setPrice(BigDecimal.TEN);
//                        o.addOrderItem(oi);
//                        orderServiceDao.createOrder(o);
//                        LOGGER.info("Creating order {} ",o);
//                        break;
//                    case 2:
//                        break;
//                    case 3:
//                        System.out.println("Enter service name");
//                        String queriedService = sc.next();
//                        break;
//                }
//            } while (!sc.nextLine().equals("quit"));
            System.out.println("Shutting down service registry process");
        }
    }
}
