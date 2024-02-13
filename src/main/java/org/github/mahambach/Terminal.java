package org.github.mahambach;

import lombok.RequiredArgsConstructor;
import org.github.mahambach.order.Order;
import org.github.mahambach.order.OrderStatus;
import org.github.mahambach.product.Product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RequiredArgsConstructor
public class Terminal {

    private final ShopService shopService;

    public void executeFromFile(String fileName) {
        Path path = Paths.get(fileName);
        try {
            String[] lines = Files.readAllLines(path).toArray(new String[0]);

            Map<String, String> orderMapAliasToId = new HashMap<>();
            Map<String, String> orderMapIdToAlias = new HashMap<>();
            Map<String, String> productMapAliasToId = new HashMap<>();
            Map<String, String> productMapIdToAlias = new HashMap<>();

            for (String line : lines) {
                commandLineExecute(line, orderMapAliasToId, orderMapIdToAlias, productMapAliasToId, productMapIdToAlias);
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Lesen der Datei: " + e.getMessage());
        }
    }

    private void commandLineExecute(String input,
                                    Map<String, String> orderMapAliasToId,
                                    Map<String, String> orderMapIdToAlias,
                                    Map<String, String> productMapAliasToId,
                                    Map<String, String> productMapIdToAlias) {
        String[] inputCommand = input.split(" ");
        switch (inputCommand[0]) {
            case "addProduct" -> {
                // Der Input sollte die folgende Form haben: addProduct <productName> <quantity>
                Product newProduct = shopService.addProduct(inputCommand[1], Integer.valueOf(inputCommand[2]));
                System.out.println("Füge Produkt " + inputCommand[1] + " mit der ID " + newProduct.id() + " hinzu.");
                productMapAliasToId.put(inputCommand[1], newProduct.id());
                productMapIdToAlias.put(newProduct.id(), inputCommand[1]);
            }
            case "addOrder" -> {
                // Der Input sollte die folgende Form haben: addOrder <orderId> <productId1> <productId2> ...
                System.out.println(
                        "Füge Bestellung \'"
                                + inputCommand[1]
                                + "\' mit den Produkten "
                                + Arrays.asList(inputCommand).subList(2, inputCommand.length)
                                + " hinzu."
                );
                List<String> productIds = new ArrayList<>();
                List<Integer> productQuantities = new ArrayList<>();
                for (int i = 2; i < inputCommand.length; i=i+2) {
                    productIds.add(productMapAliasToId.get(inputCommand[i]));
                    productQuantities.add(i+1);
                }
                Order newOrder = shopService.addOrder(productIds, productQuantities);
                orderMapAliasToId.put(inputCommand[1], newOrder.id());
                orderMapIdToAlias.put(newOrder.id(), inputCommand[1]);
            }
            case "getAllOrdersWithStatus" -> {
                // Der Input sollte die folgende Form haben: getAllOrdersWithStatus <status>
                System.out.println("Bestellungen mit Status " + inputCommand[1] + ":");
                List<Order> orders = shopService.getAllOrdersWithStatus(OrderStatus.valueOf(inputCommand[1]));
                for (Order order : orders) {
                    System.out.println(order.withId(orderMapAliasToId.get(order.id())));
                }
            }
            case "getOldestOrderPerStatus" -> {
                // Der Input sollte die folgende Form haben: getOldestOrderPerStatus
                Map<OrderStatus, Order> oldestOrderPerStatus = shopService.getOldestOrderPerStatus();
                for (OrderStatus orderStatus : OrderStatus.values()) {
                    Order oldestOrderWithStatus = oldestOrderPerStatus.get(orderStatus);
                    System.out.println(
                            "Älteste Bestellung mit Status "
                                    + " ".repeat(11 - orderStatus.toString().length())
                                    + "'"
                                    + orderStatus
                                    + "': "
                                    + oldestOrderWithStatus.withId(orderMapIdToAlias.get(oldestOrderWithStatus.id()))
                                    + "'."
                    );
                }
            }
            case "setStatus" -> {
                // Der Input sollte die folgende Form haben: setStatus <orderId> <status>
                System.out.println("Setze Status von Bestellung \'" + inputCommand[1] + "\' auf \'" + inputCommand[2] + "\'.");
                shopService.updateOrder(orderMapAliasToId.get(inputCommand[1]), OrderStatus.valueOf(inputCommand[2]));
            }
            case "printOrders" -> {
                // Der Input sollte die folgende Form haben: printOrders
                System.out.println("Bestellungen nach Status sortiert (PROCESSING > IN_DELIVERY > COMPLETE):");
                for (OrderStatus orderStatus : OrderStatus.values()) {
                    List<Order> orders = shopService.getAllOrdersWithStatus(orderStatus);
                    for (Order order : orders) {
                        System.out.println(order.withId(orderMapIdToAlias.get(order.id())));
                    }
                }
            }
            default -> System.out.println("Ungültiger Befehl: " + input);
        }
    }
}
