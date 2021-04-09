package com.onboarding.payu.repository.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Entity that represents a product order relation.
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
@Table(name = "order_product")
public class OrderProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_order_product", updatable = false, nullable = false)
	private Integer idOrderProduct;

	@Column(name = "id_purchase_order")
	private Integer idPurchaseOrder;

	@ManyToOne
	@JoinColumn(name = "id_product", updatable = false, nullable = false)
	private Product product;

	@Column(name = "quantity")
	private Integer quantity;

	@Column(name = "unit_value")
	private BigDecimal unitValue;
}
