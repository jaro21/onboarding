package com.onboarding.payu.repository;

import com.onboarding.payu.repository.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository to manage {@link OrderProduct} instances.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IOrderProductRepository extends JpaRepository<OrderProduct, Integer> {

	@Modifying
	@Query("delete OrderProduct where idPurchaseOrder = :id")
	void deleteByIdPurchaseOrder(@Param("id") Integer id);
}
