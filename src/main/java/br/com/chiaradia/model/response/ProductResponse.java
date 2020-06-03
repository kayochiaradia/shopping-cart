package br.com.chiaradia.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse implements Serializable {

    private Integer id;

    private String name;

    private String description;

    @DecimalMin(value = "0.00")
    private BigDecimal price;
}
