package br.com.chiaradia.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartResponse implements Serializable {

    private Map<String, Integer> products;

    private int totalAmountOfProducts;

    @DecimalMin(value = "0.00")
    private BigDecimal totalPrice;
}
