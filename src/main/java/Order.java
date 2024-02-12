import lombok.With;

import java.time.Instant;
import java.util.List;

@With
public record Order(
        String id,
        OrderStatus status,
        List<Product> products,

        Instant orderDate
) {

}
