import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderListRepoTest {

    @Test
    void getOrders() {
        //GIVEN
        OrderListRepo repo = new OrderListRepo();

        Product product = new Product("1", "Apfel");
        Order newOrder = new Order("1", OrderStatus.IN_DELIVERY, List.of(product), Instant.now());
        repo.addOrder(newOrder);

        //WHEN
        List<Order> actual = repo.getOrders();

        //THEN
        List<Order> expected = new ArrayList<>();
        Product product1 = new Product("1", "Apfel");
        expected.add(new Order("1", OrderStatus.IN_DELIVERY, List.of(product), Instant.now()));

        assertEquals(actual, expected);
    }

    @Test
    void getOrderById() {
        //GIVEN
        OrderListRepo repo = new OrderListRepo();

        Product product = new Product("1", "Apfel");
        Order newOrder = new Order("1", OrderStatus.IN_DELIVERY, List.of(product), Instant.now());
        repo.addOrder(newOrder);

        //WHEN
        Order actual = repo.getOrderById("1");

        //THEN
        Product product1 = new Product("1", "Apfel");
        Order expected = new Order("1", OrderStatus.IN_DELIVERY, List.of(product), Instant.now());

        assertEquals(actual, expected);
    }

    @Test
    void addOrder() {
        //GIVEN
        OrderListRepo repo = new OrderListRepo();
        Product product = new Product("1", "Apfel");
        Order newOrder = new Order("1", OrderStatus.IN_DELIVERY, List.of(product), Instant.now());

        //WHEN
        Order actual = repo.addOrder(newOrder);

        //THEN
        Product product1 = new Product("1", "Apfel");
        Order expected = new Order("1", OrderStatus.IN_DELIVERY, List.of(product), Instant.now());
        assertEquals(actual, expected);
        assertEquals(repo.getOrderById("1"), expected);
    }

    @Test
    void removeOrder() {
        //GIVEN
        OrderListRepo repo = new OrderListRepo();

        //WHEN
        repo.removeOrder("1");

        //THEN
        assertNull(repo.getOrderById("1"));
    }

    @Test
    void updateOrder() {
        //GIVEN
        OrderListRepo repo = new OrderListRepo();
        Product product = new Product("1", "Apfel");
        Order newOrder = new Order("1", OrderStatus.IN_DELIVERY, List.of(product), Instant.now());
        repo.addOrder(newOrder);

        //WHEN
        repo.updateOrder("1", OrderStatus.IN_DELIVERY);

        //THEN
        Product product1 = new Product("1", "Apfel");
        Order expected = new Order("1", OrderStatus.IN_DELIVERY, List.of(product1), Instant.now());
        assertEquals(repo.getOrderById("1"), expected);
    }
}
