import lombok.With;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@With
public record Order(
        String id,
        OrderStatus status,
        Instant orderDate,
        List<Product> products
) {
    @Override
    public String toString() {
        String string = String.format("Order{id='%s', status=%11s, orderDate=%s, products=[",
                id,
                status,
                LocalDateTime.ofInstant(orderDate, ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))
        );
        for (Product product : products) {
            string += product.name() + ", ";
        }
        string = string.substring(0, string.length() - 2);
        string += "]}";
        return string;
    }
}
