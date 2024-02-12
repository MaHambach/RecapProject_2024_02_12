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
        ShopService shopService = new ShopService(productRepo, orderMapRepo);



    }
}
