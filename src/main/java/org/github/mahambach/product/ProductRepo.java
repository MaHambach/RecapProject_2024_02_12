package org.github.mahambach.product;

import org.github.mahambach.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepo {
    private List<Product> products;

    public ProductRepo() {
        products = new ArrayList<>();
    }

    public List<Product> getProducts() {
        return products;
    }

    public Optional<Product> getProductById(String id) {
        for (Product product : products) {
            if (product.id().equals(id)) {
                return Optional.of(product);
            }
        }
        return Optional.empty();
    }

    public Product addProduct(Product newProduct) {
        products.add(newProduct);
        return newProduct;
    }

    public void removeProduct(String id) {
        for (Product product : products) {
           if (product.id().equals(id)) {
               products.remove(product);
               return;
           }
        }
    }

    public int getStock(String id) {
        for (Product product : products) {
            if (product.id().equals(id)) {
                return product.quantity();
            }
        }
        return 0;
    }

    public int changeStock(String id, int change) throws OutOfStockException{
        Product product = getProductById(id).orElseThrow(() -> new OutOfStockException("Fehler: Produkt nicht gefunden!"));

        if (product.quantity() + change < 0) {
            throw new OutOfStockException("Fehler: Nicht genügend '" + product.name() + "' auf Lager!");
        }

        product = product.withQuantity(product.quantity() + change);

        return product.quantity();
    }
}
