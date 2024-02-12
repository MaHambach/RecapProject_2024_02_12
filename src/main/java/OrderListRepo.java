import java.util.*;

public class OrderListRepo implements OrderRepo{
    private List<Order> orders = new ArrayList<>();

    public List<Order> getOrders() {
        return orders;
    }

    public Order getOrderById(String id) {
        for (Order order : orders) {
            if (order.id().equals(id)) {
                return order;
            }
        }
        return null;
    }

    public Order addOrder(Order newOrder) {
        orders.add(newOrder);
        return newOrder;
    }

    public void removeOrder(String id) {
        for (Order order : orders) {
            if (order.id().equals(id)) {
                orders.remove(order);
                return;
            }
        }
    }

    @Override
    public List<Order> getAllOrdersWithStatus(OrderStatus status) {
        return orders.stream()
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
