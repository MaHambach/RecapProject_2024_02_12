package org.github.mahambach.product;

import org.github.mahambach.order.Order;
import org.github.mahambach.order.OrderMapRepo;
import org.github.mahambach.order.OrderStatus;
import org.github.mahambach.product.Product;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapRepoTest {

    @Test
    void getOrders() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();
        Product product = new Product("1", "Apfel", 5);
        Instant now = Instant.now();
        Order newOrder = new Order("A", OrderStatus.IN_DELIVERY, now, List.of(product));
        repo.addOrder(newOrder);

        //WHEN
        List<Order> actual = repo.getOrders();

        //THEN
        List<Order> expected = new ArrayList<>();
        Product product1 = new Product("1", "Apfel", 5);
        expected.add(new Order("A", OrderStatus.IN_DELIVERY, now, List.of(product1)));

        assertEquals(actual, expected);
    }

    @Test
    void getOrderById() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();
        Product product = new Product("1", "Apfel", 5);
        Instant now = Instant.now();
        Order newOrder = new Order("A", OrderStatus.IN_DELIVERY, now, List.of(product));
        repo.addOrder(newOrder);

        //WHEN
        Order actual = repo.getOrderById("A");

        //THEN
        Product product1 = new Product("1", "Apfel", 5);
        Order expected = new Order("A", OrderStatus.IN_DELIVERY, now, List.of(product1));

        assertEquals(actual, expected);
    }

    @Test
    void addOrder() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();
        Product product = new Product("1", "Apfel", 5);
        Instant now = Instant.now();
        Order newOrder = new Order("A", OrderStatus.IN_DELIVERY, now, List.of(product));

        //WHEN
        Order actual = repo.addOrder(newOrder);

        //THEN
        Product product1 = new Product("1", "Apfel", 5);
        Order expected = new Order("A", OrderStatus.IN_DELIVERY, now, List.of(product1));
        assertEquals(actual, expected);
        assertEquals(repo.getOrderById("A"), expected);
    }

    @Test
    void removeOrder() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();

        //WHEN
        repo.removeOrder("1");

        //THEN
        assertNull(repo.getOrderById("1"));
    }

    @Test
    void updateOrder() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();
        Product product = new Product("1", "Apfel", 5);
        Instant now = Instant.now();
        Order newOrder = new Order("A", OrderStatus.PROCESSING, now, List.of(product));
        repo.addOrder(newOrder);

        //WHEN
        repo.updateOrder("A", OrderStatus.IN_DELIVERY);

        //THEN
        Product product1 = new Product("1", "Apfel", 5);
        Order expected = new Order("A", OrderStatus.IN_DELIVERY, now, List.of(product1));
        assertEquals(repo.getOrderById("A"), expected);
    }
}
