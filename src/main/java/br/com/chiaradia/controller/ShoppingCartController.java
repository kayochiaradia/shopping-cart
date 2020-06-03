package br.com.chiaradia.controller;

import br.com.chiaradia.Paths;
import br.com.chiaradia.model.response.ProductResponse;
import br.com.chiaradia.model.response.ShoppingCartResponse;
import br.com.chiaradia.service.ShoppingCartService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "Shopping Cart")
@RequestMapping(Paths.SHOPPING_CART)
public class ShoppingCartController {

    private final ModelMapper mapper;

    private final ShoppingCartService shoppingCartService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ShoppingCartResponse> getShoppingCartSummary() {
        ShoppingCartResponse shoppingCartVo = shoppingCartService.getShoppingCartSummary();
        return ResponseEntity.ok(shoppingCartVo);
    }

    @RequestMapping(value = "/add/{productId}", method = RequestMethod.PUT)
    public ResponseEntity<ShoppingCartResponse> addProductToCart(@PathVariable("productId") Integer productId, Integer amount) {
        shoppingCartService.addProductToCart(productId, amount);
        return getShoppingCartSummary();
    }

    @RequestMapping(value = "/remove/{productId}", method = RequestMethod.PUT)
    public ResponseEntity<ShoppingCartResponse> removeProductFromCart(@PathVariable("productId") Integer productId) {
        shoppingCartService.removeProductFromCart(productId);
        return getShoppingCartSummary();
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<List<ProductResponse>> searchProduct(@RequestParam("name") String productName) {
        List<ProductResponse> products = shoppingCartService.searchProduct(productName).stream()
                .map(product -> mapper.map(product, ProductResponse.class)).collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @RequestMapping(value = "/allproducts", method = RequestMethod.GET)
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> products = shoppingCartService.getAllProducts().stream()
                .map(product -> mapper.map(product, ProductResponse.class)).collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }
}
