import lombok.RequiredArgsConstructor;

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
}




















