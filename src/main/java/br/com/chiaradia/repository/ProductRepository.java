package br.com.chiaradia.repository;

import br.com.chiaradia.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByNameIgnoreCaseContaining(String productName);
}
