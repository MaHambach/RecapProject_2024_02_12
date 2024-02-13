package org.github.mahambach;

import org.github.mahambach.order.OrderMapRepo;
import org.github.mahambach.product.Product;
import org.github.mahambach.product.ProductRepo;

public class Main {
    public static void main(String[] args) {
        ProductRepo productRepo = new ProductRepo();
        productRepo.addProduct(new Product("1", "Buch"));
        productRepo.addProduct(new Product("2", "Stift"));
        productRepo.addProduct(new Product("3", "Hefter"));
        productRepo.addProduct(new Product("4", "Locher"));
        productRepo.addProduct(new Product("5", "Taschenrechner"));

        OrderMapRepo orderMapRepo = new OrderMapRepo();
        IdService idService = new IdService();
        ShopService shopService = new ShopService(productRepo, orderMapRepo, idService);
//        Order order1 = shopService.addOrder(List.of("1", "2", "3", "4", "5"));
//        Order order2 = shopService.addOrder(List.of("1", "1", "1", "4", "4"));
//        Order order3 = shopService.addOrder(List.of("2", "2", "2", "4", "5"));
//        Order order4 = shopService.addOrder(List.of("2", "2", "2", "2", "5"));
//
//        List<Order> orders = shopService.getAllOrdersWithStatus(OrderStatus.PROCESSING);
//
//        for(Order order : orders){
//            System.out.println(order);
//        }
//        System.out.println();
//        shopService.updateOrder(order1.id(), OrderStatus.COMPLETED);
//        shopService.updateOrder(order2.id(), OrderStatus.IN_DELIVERY);
//
//        Map<OrderStatus, Order> oldestOrderPerStatus = shopService.getOldestOrderPerStatus();
//        for(OrderStatus status : OrderStatus.values()){
//            System.out.printf("Älteste Bestellung mit Status %11s: %s%n", status, oldestOrderPerStatus.get(status));
//        }

        shopService.executeFromFile("src/transactions.txt");
        System.out.println();
        System.out.println("Alle Bestellungen:");
        shopService.printOrders();
        //shopService.readFromFile("src/transactions.txt");
    }
}
