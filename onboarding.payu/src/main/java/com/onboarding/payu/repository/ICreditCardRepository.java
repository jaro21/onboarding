package com.onboarding.payu.repository;

import java.util.List;
import java.util.Optional;

import com.onboarding.payu.repository.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to manage {@link CreditCard} instances.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public interface ICreditCardRepository extends JpaRepository<CreditCard, Integer> {

	Optional<List<CreditCard>> findByIdCustomer(Integer idCustomer);

	Optional<CreditCard> findByToken(String token);
}