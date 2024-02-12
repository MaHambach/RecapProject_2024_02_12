import java.util.List;

public interface OrderRepo {

    List<Order> getOrders();

    Order getOrderById(String id);

    Order addOrder(Order newOrder);

    void removeOrder(String id);

    List<Order> getAllOrdersWithStatus(OrderStatus status);

    void updateOrder(String orderID, OrderStatus status);
}
