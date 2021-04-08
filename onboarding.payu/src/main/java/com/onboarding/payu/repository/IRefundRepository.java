package com.onboarding.payu.repository;

import com.onboarding.payu.repository.entity.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to manage {@link Refund} instances.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IRefundRepository extends JpaRepository<Refund, Integer> {
}