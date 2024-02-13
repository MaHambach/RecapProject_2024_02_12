package org.github.mahambach.product;

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
    void addOrderTest_whenProductList_thenProductList() {
        //GIVEN
        ShopService shopService = new ShopService();
        Product product = shopService.addProduct("Apfel", 5);
        List<String> productsIds = List.of("1");
        List<Integer> productQuantities = List.of(5);
        //WHEN
        Order actual = shopService.addOrder(productsIds, productQuantities);

        //THEN
        Order expected = new Order("-1", OrderStatus.IN_DELIVERY, Instant.now(), List.of(product));
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
    }

    @Test
    void addOrderTest_whenInvalidProductId_expectNull() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1", "2");
        List<Integer> productQuantities = List.of(5, 5);
        //WHEN

        //THEN
        assertThrows(IllegalArgumentException.class, () -> shopService.addOrder(productsIds,productQuantities));
    }
}
