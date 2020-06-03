# ShoppingCart

## Sobre

Esse é um projeto SpringBoot. A ideia era cirar um carrinho de compras basico.

BAnco de dados em memoria H2.

## Como rodar

O que você precisa instalar ou ter no seu computador:
- Java 8
- Maven

Você pode rodar o programa pela linha de comando pelo MAVEN.
Vai para a pasta raiz da aplicação e digite:
```
./mvnw spring-boot:run
```

Ou use a sua IDE(f.e. Eclipse)

Essa aplicação devera rodar em alguns segundos.

Api url: `http://localhost:8080/api/shoppingCart`

Em `/src/main/resources/application.properties` é possivel mudar o banco e a porta.

## Ferramentas de ajuda

### LOMBOK

Esse projeto utiliza o lombok, então se não estiver instalado entrar no link e instalar:

https://projectlombok.org/download

### SWAGGER

Abre o Browser e digite http://localhost:8080/swagger-ui.htm

### H2 Database web interface

Abre o Browser e digite `http://localhost:8080/h2-console`

no campo **JDBC URL** coloque 
```
jdbc:h2:mem:shopping_cart_db
```

e aperte em 'Connect'.

O banco está preenchido com alguns produtos de exemplo quand a plicação da o start.

