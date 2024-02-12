import lombok.RequiredArgsConstructor;

import java.io.IOException;
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

        Order newOrder = new Order(UUID.randomUUID().toString(), OrderStatus.PROCESSING, Instant.now(), products);

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

    public Product addProduct(Product newProduct) {
        return productRepo.addProduct(newProduct);
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
        if (inputCommand[0].equals("addProduct")) {
            String id = idService.generateId().toString();
            System.out.println("Füge Produkt " + inputCommand[1] + " mit der ID " + id + " hinzu.");
            Product newProduct = new Product(id, inputCommand[1]);
            addProduct(newProduct);

        } else if (inputCommand[0].equals("addOrder")) {
            System.out.println(
                    "Füge Bestellung \'"
                            + inputCommand[1]
                            + "\' mit den Produkten "
                            + Arrays.asList(inputCommand).subList(2, inputCommand.length)
                            + " hinzu."
            );
            addOrder(inputCommand[1], Arrays.asList(inputCommand).subList(2, inputCommand.length));

        } else if (inputCommand[0].equals("getAllOrdersWithStatus")) {
            List<Order> orders = getAllOrdersWithStatus(OrderStatus.valueOf(inputCommand[1]));
            for (Order order : orders) {
                System.out.println(order);
            }

        } else if (inputCommand[0].equals("getOldestOrderPerStatus")) {
            Map<OrderStatus, Order> oldestOrderPerStatus = getOldestOrderPerStatus();
            for (OrderStatus orderStatus : OrderStatus.values()) {
                System.out.println("Älteste Bestellung mit Status \'" + orderStatus + "\': " + oldestOrderPerStatus.get(orderStatus) +"\'.");
            }
        } else if (inputCommand[0].equals("setStatus")){
            System.out.println("Setze Status von Bestellung \'" + inputCommand[1] + "\' auf \'" + inputCommand[2] + "\'.");
            updateOrder(inputCommand[1], OrderStatus.valueOf(inputCommand[2]));

        } else if (inputCommand[0].equals("printOrders")){
            for(OrderStatus orderStatus : OrderStatus.values()){
                System.out.println("Bestellungen mit Status " + orderStatus + ":");
                List<Order> orders = getAllOrdersWithStatus(orderStatus);
                for(Order order : orders){
                    System.out.println(order);
                }
            }
        }
    }
}




















