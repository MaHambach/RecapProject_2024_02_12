package org.github.mahambach;

import lombok.RequiredArgsConstructor;
import org.github.mahambach.order.Order;
import org.github.mahambach.order.OrderStatus;
import org.github.mahambach.product.Product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class Terminal {

    private final ShopService shopService;

//    public void readFromFile(String fileName){
//        Path path = Paths.get(fileName);
//        try {
//            Files.readAllLines(path).forEach(this::commandLineExecute);
//        } catch (IOException e) {
//            System.err.println("Fehler beim Lesen der Datei: " + e.getMessage());
//        }
//        //TODO: Funktion so überarbeiten, dass die addOrder(List<String> productIds) Methode aufgerufen wird und nicht die Andere.
//        //TODO: Dazu müssen die beiden Funktionen zusammengeführt werden und eine Map, welche die OrderId aus der Datei und die zugewiesene OrderId verknüpft.
//    }

//    public void commandLineExecute(String input) {
//        String[] inputCommand = input.split(" ");
//        switch (inputCommand[0]) {
//            case "addProduct" -> {
//                Product newProduct = shopService.addProduct(inputCommand[1]);
//                System.out.println("Füge Produkt " + inputCommand[1] + " mit der ID " + newProduct.id() + " hinzu.");
//            }
//            case "addOrder" -> {
//                System.out.println(
//                        "Füge Bestellung \'"
//                                + inputCommand[1]
//                                + "\' mit den Produkten "
//                                + Arrays.asList(inputCommand).subList(2, inputCommand.length)
//                                + " hinzu."
//                );
//                shopService.addOrder(inputCommand[1], Arrays.asList(inputCommand).subList(2, inputCommand.length));
//            }
//            case "getAllOrdersWithStatus" -> {
//                List<Order> orders = shopService.getAllOrdersWithStatus(OrderStatus.valueOf(inputCommand[1]));
//                for (Order order : orders) {
//                    System.out.println(order);
//                }
//            }
//            case "getOldestOrderPerStatus" -> {
//                Map<OrderStatus, Order> oldestOrderPerStatus = shopService.getOldestOrderPerStatus();
//                for (OrderStatus orderStatus : OrderStatus.values()) {
//                    System.out.println("Älteste Bestellung mit Status \'" + orderStatus + "\': " + oldestOrderPerStatus.get(orderStatus) + "\'.");
//                }
//            }
//            case "setStatus" -> {
//                System.out.println("Setze Status von Bestellung \'" + inputCommand[1] + "\' auf \'" + inputCommand[2] + "\'.");
//                shopService.updateOrder(inputCommand[1], OrderStatus.valueOf(inputCommand[2]));
//            }
//            case "printOrders" -> {
//                for (OrderStatus orderStatus : OrderStatus.values()) {
//                    System.out.println("Bestellungen mit Status " + orderStatus + ":");
//                    List<Order> orders = shopService.getAllOrdersWithStatus(orderStatus);
//                    for (Order order : orders) {
//                        System.out.println(order);
//                    }
//                }
//            }
//            default -> System.out.println("Ungültiger Befehl: " + input);
//        }
//    }

    public void executeFromFile(String fileName) {
        Path path = Paths.get(fileName);
        try {
            String[] lines = Files.readAllLines(path).toArray(new String[0]);

            Map<String, String> orderMapAliasToId = new HashMap<>();
            Map<String, String> orderMapIDToAlias = new HashMap<>();
            Map<String, String> productIds = new HashMap<>();

            for (String line : lines) {
                commandLineExecute(line, orderMapAliasToId, orderMapIDToAlias, productIds);
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Lesen der Datei: " + e.getMessage());
        }
    }

    private void commandLineExecute(String input,
                                    Map<String, String> orderMapAliasToId,
                                    Map<String, String> orderMapIDToAlias,
                                    Map<String, String> productIds) {
        String[] inputCommand = input.split(" ");
        switch (inputCommand[0]) {
            case "addProduct" -> {
                // Der Input sollte die folgende Form haben: addProduct <productName>
                Product newProduct = shopService.addProduct(inputCommand[1]);
                System.out.println("Füge Produkt " + inputCommand[1] + " mit der ID " + newProduct.id() + " hinzu.");
                productIds.put(inputCommand[1], newProduct.id());
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
                Order newOrder = shopService.addOrder(Arrays.asList(inputCommand).subList(2, inputCommand.length));
                orderMapAliasToId.put(inputCommand[1], newOrder.id());
                orderMapIDToAlias.put(newOrder.id(), inputCommand[1]);
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
                                    + oldestOrderWithStatus.withId(orderMapIDToAlias.get(oldestOrderWithStatus.id()))
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
                        System.out.println(order.withId(orderMapIDToAlias.get(order.id())));
                    }
                }
            }
            default -> System.out.println("Ungültiger Befehl: " + input);
        }
    }
}
