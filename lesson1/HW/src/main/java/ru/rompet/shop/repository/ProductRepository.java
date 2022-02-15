package ru.rompet.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rompet.shop.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
