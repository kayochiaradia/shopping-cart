package br.com.chiaradia.service;

import br.com.chiaradia.entities.Product;
import br.com.chiaradia.model.response.ShoppingCartResponse;

import java.util.List;

public interface ShoppingCartService {

    ShoppingCartResponse getShoppingCartSummary();

    void addProductToCart(Integer productId, Integer amount);

    void removeProductFromCart(Integer productId);

    List<Product> searchProduct(String productName);

    List<Product> getAllProducts();
}
