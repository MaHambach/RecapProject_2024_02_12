import org.github.mahambach.order.Order;
import org.github.mahambach.order.OrderStatus;
import org.github.mahambach.product.Product;
import org.github.mahambach.ShopService;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = new Order("-1", OrderStatus.IN_DELIVERY, Instant.now(), List.of(new Product("1", "Apfel")));
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
    }

    @Test
    void addOrderTest_whenInvalidProductId_expectNull() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1", "2");

        //WHEN

        //THEN
        assertThrows(IllegalArgumentException.class, () -> shopService.addOrder(productsIds));
    }
}
