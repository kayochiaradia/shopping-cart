package br.com.chiaradia.service.impl;

import br.com.chiaradia.entities.Product;
import br.com.chiaradia.exceptions.DataNotFoundException;
import br.com.chiaradia.repository.ProductRepository;
import br.com.chiaradia.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@Service
@RequiredArgsConstructor
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public void removeProduct(Integer productId) {
        Product productToRemove = findProduct(productId, "Product to delete not found.");
        productRepository.delete(productToRemove);
    }

    public Product updateProduct(Integer productId, Product product) {
        Product productToUpdate = findProduct(productId, "Product to update not found.");

        productToUpdate.setName(product.getName());
        productToUpdate.setDescription(product.getDescription());
        productToUpdate.setPrice(product.getPrice());

        return productRepository.save(productToUpdate);
    }

    public List<Product> searchProduct(String productName) {
        return productRepository.findAllByNameIgnoreCaseContaining(productName);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    private Product findProduct(Integer productId, String message) {
        return productRepository.findById(productId).orElseThrow(() -> new DataNotFoundException(message));
    }
}
