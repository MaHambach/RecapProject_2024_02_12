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

    public Order addOrder(String orderId, List<String> productIds) throws  IllegalArgumentException{
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Optional<Product> productToOrder = productRepo.getProductById(productId);
            if (productToOrder.isEmpty()) {
                throw new IllegalArgumentException("Product mit der Id: " + productId + " konnte nicht bestellt werden!");
            }
            products.add(productToOrder.get());
        }

        Order newOrder = new Order(orderId, OrderStatus.PROCESSING, Instant.now(), products);

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


    public void readFromFile(String fileName){
        Path path = Paths.get(fileName);
        try {
            Files.readAllLines(path).forEach(this::commandLineExecute);
        } catch (IOException e) {
            System.err.println("Fehler beim Lesen der Datei: " + e.getMessage());
        }
        //TODO: Funktion so überarbeiten, dass die addOrder(List<String> productIds) Methode aufgerufen wird und nicht die Andere.
        //TODO: Dazu müssen die beiden Funktionen zusammengeführt werden und eine Map, welche die OrderId aus der Datei und die zugewiesene OrderId verknüpft.
    }

    public void commandLineExecute(String input) {
        String[] inputCommand = input.split(" ");
        switch (inputCommand[0]) {
            case "addProduct" -> {
                Product newProduct = addProduct(inputCommand[1]);
                System.out.println("Füge Produkt " + inputCommand[1] + " mit der ID " + newProduct.id() + " hinzu.");
            }
            case "addOrder" -> {
                System.out.println(
                        "Füge Bestellung \'"
                                + inputCommand[1]
                                + "\' mit den Produkten "
                                + Arrays.asList(inputCommand).subList(2, inputCommand.length)
                                + " hinzu."
                );
                addOrder(inputCommand[1], Arrays.asList(inputCommand).subList(2, inputCommand.length));
            }
            case "getAllOrdersWithStatus" -> {
                List<Order> orders = getAllOrdersWithStatus(OrderStatus.valueOf(inputCommand[1]));
                for (Order order : orders) {
                    System.out.println(order);
                }
            }
            case "getOldestOrderPerStatus" -> {
                Map<OrderStatus, Order> oldestOrderPerStatus = getOldestOrderPerStatus();
                for (OrderStatus orderStatus : OrderStatus.values()) {
                    System.out.println("Älteste Bestellung mit Status \'" + orderStatus + "\': " + oldestOrderPerStatus.get(orderStatus) + "\'.");
                }
            }
            case "setStatus" -> {
                System.out.println("Setze Status von Bestellung \'" + inputCommand[1] + "\' auf \'" + inputCommand[2] + "\'.");
                updateOrder(inputCommand[1], OrderStatus.valueOf(inputCommand[2]));
            }
            case "printOrders" -> {
                for (OrderStatus orderStatus : OrderStatus.values()) {
                    System.out.println("Bestellungen mit Status " + orderStatus + ":");
                    List<Order> orders = getAllOrdersWithStatus(orderStatus);
                    for (Order order : orders) {
                        System.out.println(order);
                    }
                }
            }
            default -> System.out.println("Ungültiger Befehl: " + input);
        }
    }
}




















