import java.util.List;

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
        shopService.addOrder(List.of("1", "2", "3", "4", "5"));
        shopService.addOrder(List.of("1", "1", "1", "4", "4"));
        shopService.addOrder(List.of("2", "2", "2", "4", "5"));

        List<Order> orders = shopService.getAllOrdersWithStatus(OrderStatus.PROCESSING);

        for(Order order : orders){
            System.out.println(order);
        }

    }
}
