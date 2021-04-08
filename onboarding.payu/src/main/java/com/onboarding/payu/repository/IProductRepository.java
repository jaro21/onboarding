package com.onboarding.payu.repository;

import java.util.List;
import java.util.Optional;

import com.onboarding.payu.repository.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository to manage {@link Product} instances.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IProductRepository extends JpaRepository<Product, Integer> {

	Optional<Product> findByCode(String code);

	Optional<List<Product>> findByActive(Integer active);

	@Modifying
	@Query("update Product set stock = :stock where idProduct = :id")
	Integer updateStockById(@Param("stock") Integer stock, @Param("id") Integer id);
}