package br.com.chiaradia.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ShoppingCartProperties {

    @Value("${shopping.cart.external.url}")
    private String externalUrl;

    @Value("${shopping.cart.initial.status}")
    private String initialStatus;
}
