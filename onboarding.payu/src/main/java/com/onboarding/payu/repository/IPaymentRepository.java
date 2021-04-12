package com.onboarding.payu.repository;

import com.onboarding.payu.repository.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository to manage {@link Payment} instances.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IPaymentRepository extends JpaRepository<Payment, Integer> {

	@Modifying
	@Query("update Payment set status = :status where idPayment = :id")
	Integer updateStatusById(@Param("status") String status, @Param("id") Integer id);
}