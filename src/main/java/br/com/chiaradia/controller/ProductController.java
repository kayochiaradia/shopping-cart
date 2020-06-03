package br.com.chiaradia.controller;

import br.com.chiaradia.Paths;
import br.com.chiaradia.config.ShoppingCartProperties;
import br.com.chiaradia.entities.Product;
import br.com.chiaradia.exceptions.models.ExceptionResponse;
import br.com.chiaradia.model.request.ProductRequest;
import br.com.chiaradia.model.response.ProductResponse;
import br.com.chiaradia.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "Product")
@RequestMapping(Paths.PRODUCTS)
public class ProductController {

    private final ShoppingCartProperties properties;

    private final ModelMapper mapper;

    private final ProductService productService;

    @ApiOperation(value = "ADD a Product")
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "Product successfully added", responseHeaders = {
                    @ResponseHeader(name = "Location", response = String.class, description = "A URL of the newly added resource")}),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "The request sent by the client is incorrect", response = ExceptionResponse.class),
            @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "An unexpected error occurred!")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/add/")
    public ResponseEntity<Void> addProduct(@Valid @RequestBody ProductRequest productRequest) {
        Product product = mapper.map(productRequest, Product.class);
        product = productService.addProduct(product);

        URI location = UriComponentsBuilder.fromUriString(properties.getExternalUrl())
                .path(Paths.PRODUCTS.concat("/{id}"))
                .buildAndExpand(product.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value = "Delete Product by id")
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "Product removed.", response = String.class),
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Product is not found.", response = ExceptionResponse.class),
            @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "An unexpected error occurred!")
    })
    @DeleteMapping(value = "/remove/{productId}")
    public ResponseEntity<String> removeProduct(@PathVariable("productId") Integer productId) {
        productService.removeProduct(productId);
        return ResponseEntity.ok().body("Produto removido com sucesso");
    }

    @ApiOperation(value = "Update Product by id")
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "Product is found.", response = ProductResponse.class),
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Product is not found.", response = ExceptionResponse.class),
            @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "An unexpected error occurred!")
    })
    @PutMapping(value = "/update/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("productId") Integer productId, @Valid @RequestBody ProductRequest productRequest) {
        Product product = mapper.map(productRequest, Product.class);
        return ResponseEntity.ok(mapper.map(productService.updateProduct(productId, product), ProductResponse.class));
    }

    @ApiOperation(value = "Find Products by product name")
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "Product is found.", response = ProductResponse.class, responseContainer = "List"),
            @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "An unexpected error occurred!")
    })
    @GetMapping(value = "/search")
    public ResponseEntity<List<ProductResponse>> searchProduct(@RequestParam("name") String productName) {
        List<ProductResponse> products = productService.searchProduct(productName).stream()
                .map(product -> mapper.map(product, ProductResponse.class)).collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @ApiOperation(value = "Find all products")
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "Products is found.", response = ProductResponse.class, responseContainer = "List"),
            @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "An unexpected error occurred!")
    })
    @GetMapping(value = "/allproducts")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> products = productService.getAllProducts().stream()
                .map(product -> mapper.map(product, ProductResponse.class)).collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }
}
