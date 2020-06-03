package br.com.chiaradia.service.impl;

import br.com.chiaradia.entities.Product;
import br.com.chiaradia.model.response.ShoppingCartResponse;
import br.com.chiaradia.repository.ProductRepository;
import br.com.chiaradia.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ProductRepository productRepository;

    private final Map<Product, Integer> products;


    public ShoppingCartResponse getShoppingCartSummary() {

        return ShoppingCartResponse.builder().products(getProductsInCart(products))
                .totalAmountOfProducts(countAmountOfProductsInCart(products)).totalPrice(countTotalPriceForProducts(products)).build();
    }

    private Map<String, Integer> getProductsInCart(Map<Product, Integer> products) {
        return products.entrySet().stream().collect(Collectors.toMap(k -> k.getKey().getName(), Map.Entry::getValue));
    }

    private int countAmountOfProductsInCart(Map<Product, Integer> products) {
        return products.values().stream().mapToInt(v -> v).sum();
    }

    private BigDecimal countTotalPriceForProducts(Map<Product, Integer> products) {
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            total = total.add(entry.getKey().getPrice().multiply(new BigDecimal(entry.getValue())));
        }
        return total;
    }

    public void addProductToCart(Integer productId, Integer amount) {
        Optional<Product> productToAdd = productRepository.findById(productId);
        if (products.containsKey(productToAdd.get())) {
            products.replace(productToAdd.get(), products.get(productToAdd) + amount);
        } else {
            products.put(productToAdd.get(), amount);
        }
    }

    public void removeProductFromCart(Integer productId) {
        Optional<Product> productToRemove = productRepository.findById(productId);
        if (products.containsKey(productToRemove)) {
            if (products.get(productToRemove) == 1) {
                products.remove(productToRemove);
            } else {
                products.replace(productToRemove.get(), products.get(productToRemove) - 1);
            }
        }
    }

    public List<Product> searchProduct(String productName) {
        List<Product> searchResult = new ArrayList<>();
        for (Product product : products.keySet()) {
            if (product.getName().toLowerCase().contains(productName.toLowerCase())) {
                searchResult.add(product);
            }
        }
        return searchResult;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
