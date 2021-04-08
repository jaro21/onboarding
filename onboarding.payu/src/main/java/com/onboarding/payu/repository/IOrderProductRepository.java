package com.onboarding.payu.repository;

import java.util.List;
import java.util.Optional;

import com.onboarding.payu.repository.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to manage {@link OrderProduct} instances.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IOrderProductRepository extends JpaRepository<OrderProduct, Integer> {

	Optional<List<OrderProduct>> findByIdPurchaseOrder(Integer idPurchaseOrder);
}
