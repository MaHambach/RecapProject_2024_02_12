package org.github.mahambach.product;

import org.github.mahambach.order.Order;
import org.github.mahambach.order.OrderListRepo;
import org.github.mahambach.order.OrderStatus;
import org.github.mahambach.product.Product;
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

        Product product = new Product("1", "Apfel", 5);
        Order newOrder = new Order("1", OrderStatus.IN_DELIVERY, Instant.now(), List.of(product));
        repo.addOrder(newOrder);

        //WHEN
        List<Order> actual = repo.getOrders();

        //THEN
        List<Order> expected = new ArrayList<>();
        Product product1 = new Product("1", "Apfel", 5);
        expected.add(new Order("1", OrderStatus.IN_DELIVERY, Instant.now(), List.of(product)));

        assertEquals(actual, expected);
    }

    @Test
    void getOrderById() {
        //GIVEN
        OrderListRepo repo = new OrderListRepo();

        Product product = new Product("1", "Apfel", 5);
        Order newOrder = new Order("1", OrderStatus.IN_DELIVERY, Instant.now(), List.of(product));
        repo.addOrder(newOrder);

        //WHEN
        Order actual = repo.getOrderById("1");

        //THEN
        Product product1 = new Product("1", "Apfel", 5);
        Order expected = new Order("1", OrderStatus.IN_DELIVERY, Instant.now(), List.of(product));

        assertEquals(actual, expected);
    }

    @Test
    void addOrder() {
        //GIVEN
        OrderListRepo repo = new OrderListRepo();
        Product product = new Product("1", "Apfel", 5);
        Instant now = Instant.now();
        Order newOrder = new Order("A", OrderStatus.IN_DELIVERY, now, List.of(product));

        //WHEN
        Order actual = repo.addOrder(newOrder);

        //THEN
        Product product1 = new Product("1", "Apfel", 5);
        Order expected = new Order("A", OrderStatus.IN_DELIVERY, now, List.of(product));
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
        Product product = new Product("1", "Apfel", 5);
        Instant now = Instant.now();
        Order newOrder = new Order("A", OrderStatus.IN_DELIVERY, now, List.of(product));
        repo.addOrder(newOrder);

        //WHEN
        repo.updateOrder("1", OrderStatus.IN_DELIVERY);

        //THEN
        Product product1 = new Product("1", "Apfel", 5);
        Order expected = new Order("A", OrderStatus.IN_DELIVERY, now, List.of(product1));
        assertEquals(repo.getOrderById("1"), expected);
    }
}
