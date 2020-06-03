package br.com.chiaradia.service;

import br.com.chiaradia.entities.Product;

import java.util.List;

public interface ProductService {
    Product addProduct(Product product);

    void removeProduct(Integer productId);

    Product updateProduct(Integer productId, Product product);

    List<Product> searchProduct(String productName);

    List<Product> getAllProducts();
}
