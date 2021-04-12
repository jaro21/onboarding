package com.onboarding.payu.repository;

import java.util.Optional;

import com.onboarding.payu.repository.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to manage {@link Customer} instances.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public interface ICustomerRepository extends JpaRepository<Customer, Integer> {

	Optional<Customer> findByDniNumber(String dniNumber);

	Optional<Customer> findByUsername(String username);
}