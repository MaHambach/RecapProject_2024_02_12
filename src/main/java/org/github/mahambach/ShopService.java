package org.github.mahambach;

import lombok.RequiredArgsConstructor;
import org.github.mahambach.order.Order;
import org.github.mahambach.order.OrderMapRepo;
import org.github.mahambach.order.OrderRepo;
import org.github.mahambach.order.OrderStatus;
import org.github.mahambach.product.Product;
import org.github.mahambach.product.ProductRepo;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.*;


@RequiredArgsConstructor
public class ShopService {
    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;
    private final IdService idService;

    public Order addOrder(List<String> productIds) throws  IllegalArgumentException{
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Optional<Product> productToOrder = productRepo.getProductById(productId);
            if (productToOrder.isEmpty()) {
                throw new IllegalArgumentException("Product mit der Id: " + productId + " konnte nicht bestellt werden!");
            }
            products.add(productToOrder.get());
        }

        Order newOrder = new Order(idService.generateId().toString(), OrderStatus.PROCESSING, Instant.now(), products);

        return orderRepo.addOrder(newOrder);
    }

    public ShopService(){
        this.productRepo = new ProductRepo();
        this.orderRepo = new OrderMapRepo();
        this.idService = new IdService();
    }

    public List<Order> getAllOrdersWithStatus(OrderStatus status) {
        return orderRepo.getAllOrdersWithStatus(status);
    }

    public void updateOrder(String orderID, OrderStatus status) {
        orderRepo.updateOrder(orderID, status);
    }

    public Product addProduct(String productName) {
        // TODO: Hinzufügen von price und stock.
        return productRepo.addProduct(new Product(idService.generateId().toString(), productName));
    }

    public Map<OrderStatus, Order> getOldestOrderPerStatus() {
        return orderRepo.getOldestOrderPerStatus();
    }

    public void printOrders() {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            List<Order> orders = getAllOrdersWithStatus(orderStatus);
            for (Order order : orders) {
                System.out.println(order);
            }
        }
    }
}




















