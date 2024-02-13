package org.github.mahambach.product;

import org.github.mahambach.product.Product;
import org.github.mahambach.product.ProductRepo;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepoTest {

    @Test
    void getProducts() {
        //GIVEN
        ProductRepo repo = new ProductRepo();

        //WHEN
        List<Product> actual = repo.getProducts();

        //THEN
        List<Product> expected = new ArrayList<>();
        expected.add(new Product("1", "Apfel", 5));
        assertEquals(actual, expected);
    }

    @Test
    void getProductById() {
        //GIVEN
        ProductRepo repo = new ProductRepo();

        //WHEN
        Optional<Product> actual = repo.getProductById("1");
        if(actual.isEmpty()) {
            fail("Product not found");
        }
        //THEN
        Product expected = new Product("1", "Apfel", 5);
        assertEquals(actual.get(), expected);
    }

    @Test
    void addProduct() {
        //GIVEN
        ProductRepo repo = new ProductRepo();
        Product newProduct = new Product("2", "Banane", 10);

        //WHEN
        Product actual = repo.addProduct(newProduct);

        //THEN
        Product expected = new Product("2", "Banane", 10);
        assertEquals(actual, expected);
        assertTrue(repo.getProductById("2").isPresent());
        assertEquals(expected, repo.getProductById("2").get());
    }

    @Test
    void removeProduct() {
        //GIVEN
        ProductRepo repo = new ProductRepo();

        //WHEN
        repo.removeProduct("1");

        //THEN
        assertEquals(Optional.empty(), repo.getProductById("1"));
    }

    @Test
    void getStock_whenNotStocked_then0() {
        //GIVEN
        ProductRepo repo = new ProductRepo();

        //WHEN
        int actual = repo.getStock("2");

        //THEN
        assertEquals(0, actual);
    }

    @Test
    void getStock_whenStocked_thenQuantity() {
        //GIVEN
        ProductRepo repo = new ProductRepo();
        repo.addProduct(new Product("1", "Apfel", 5));

        //WHEN
        int actual = repo.getStock("1");

        //THEN
        assertEquals(5, actual);
    }

    @Test
    void changeStock_whenNotStocked_thenThrow() {
        //GIVEN
        ProductRepo repo = new ProductRepo();

        //WHEN
        OutOfStockException exception = assertThrows(OutOfStockException.class, () -> repo.changeStock("2", -1));

        //THEN
        assertEquals("Fehler: Produkt nicht gefunden!", exception.getMessage());
    }

    @Test
    void changeStock_whenTooMuchTaken_thenThrow() {
        //GIVEN
        ProductRepo repo = new ProductRepo();
        repo.addProduct(new Product("1", "Apfel", 5));

        //WHEN
        OutOfStockException exception = assertThrows(OutOfStockException.class, () -> repo.changeStock("1", -6));

        //THEN
        assertEquals("Fehler: Nicht genügend 'Apfel' auf Lager!", exception.getMessage());
    }

    @Test
    void changeStock_whenTooMuchTaken_thenNoChange() {
        //GIVEN
        ProductRepo repo = new ProductRepo();
        repo.addProduct(new Product("1", "Apfel", 5));

        //WHEN
        OutOfStockException exception = assertThrows(OutOfStockException.class, () -> repo.changeStock("1", -6));

        //THEN
        assertEquals(5, repo.getStock("1"));
    }

    @Test
    void changeStock_whenStocked_thenNewQuantity() {
        //GIVEN
        ProductRepo repo = new ProductRepo();
        repo.addProduct(new Product("1", "Apfel", 5));

        //WHEN
        int actual = repo.changeStock("1", -3);

        //THEN
        assertEquals(2, actual);
    }
}
