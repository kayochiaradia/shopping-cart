package br.com.chiaradia.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String description;

    @DecimalMin(value = "0.00")
    private BigDecimal price;
}
