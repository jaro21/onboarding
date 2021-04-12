package com.onboarding.payu.repository;

import java.util.List;
import java.util.Optional;

import com.onboarding.payu.repository.entity.OrderProduct;
import com.onboarding.payu.repository.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository to manage {@link PurchaseOrder} instances.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IPurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {

	@Modifying
	@Query("update PurchaseOrder set status = :status where idPurchaseOrder = :id")
	void updateStatusById(@Param("status") String status, @Param("id") Integer id);

	Optional<List<PurchaseOrder>> findByCustomerIdCustomer(Integer idCustomer);
}