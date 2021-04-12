package com.onboarding.payu.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Entity that represents a credit card object.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "credit_card")
public class CreditCard {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_credit_card", updatable = false, nullable = false)
	private Integer idCreditCard;

	@Column(name = "id_customer", updatable = false, nullable = false)
	private Integer idCustomer;

	@Column(name = "masked_number", length = 20)
	@NotBlank(message = "Masked Number cannot be empty")
	private String maskedNumber;

	@Column(name = "payment_method", length = 20)
	@NotBlank(message = "Payment Method cannot be empty")
	private String paymentMethod;

	@Column(name = "token", length = 45)
	private String token;
}
