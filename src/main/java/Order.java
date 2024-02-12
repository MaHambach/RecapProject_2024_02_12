import lombok.With;

import java.util.List;

@With
public record Order(
        String id,
        OrderStatus status,
        List<Product> products
) {

    public Order(String id, List<Product> products) {
        this(id, OrderStatus.PROCESSING, products);
    }
}
