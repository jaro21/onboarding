package com.onboarding.payu.repository.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Entity that represents a payment object.
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
@Table(name = "payment")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_payment", updatable = false, nullable = false)
	private Integer idPayment;

	@Column(name = "id_purchase_order", updatable = false, nullable = false)
	private Integer idPurchaseOrder;

	@Column(name = "value")
	private BigDecimal value;

	@Column(name = "currency", length = 3)
	private String currency;

	@Column(name = "response_json")
	private String response_json;

	@Column(name = "status")
	private String status;
}
