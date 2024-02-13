package org.github.mahambach.order;

import java.util.*;

public class OrderMapRepo implements OrderRepo {
    private Map<String, Order> orders = new HashMap<>();

    @Override
    public List<Order> getOrders() {
        return new ArrayList<>(orders.values());
    }

    @Override
    public Order getOrderById(String id) {
        return orders.get(id);
    }

    @Override
    public Order addOrder(Order newOrder) {
        orders.put(newOrder.id(), newOrder);
        return newOrder;
    }

    @Override
    public void removeOrder(String id) {
        orders.remove(id);
    }

    @Override
    public List<Order> getAllOrdersWithStatus(OrderStatus status) {
        return orders.values().stream()
                .filter(order -> order.status().equals(status))
                .toList();
    }

    @Override
    public void updateOrder(String orderID, OrderStatus status) {
        Order order = getOrderById(orderID);
        order = order.withStatus(status);
        removeOrder(orderID);
        addOrder(order);
    }

    @Override
    public Map<OrderStatus, Order> getOldestOrderPerStatus() {
        Map<OrderStatus, Order> result = new EnumMap<>(OrderStatus.class);

        for(OrderStatus status : OrderStatus.values()){
            List<Order> ordersWithStatus = getAllOrdersWithStatus(status);
            if(ordersWithStatus.isEmpty()){
                result.put(status, null);
                continue;
            }
            Order oldestOrder = ordersWithStatus.get(0);
            for(Order order : ordersWithStatus){
                if(order.orderDate().isBefore(oldestOrder.orderDate())){
                    oldestOrder = order;
                }
            }
            result.put(status, oldestOrder);
        }
        return result;
    }
}
