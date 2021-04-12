package com.onboarding.payu.repository.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Entity that represents a product object.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_product", updatable = false, nullable = false)
	private Integer idProduct;

	@NotBlank(message = "Product name cannot be empty")
	private String name;

	@NotBlank(message = "Product code cannot be empty")
	private String code;

	@NotBlank(message = "Product description cannot be empty")
	private String description;

	@NotNull(message = "Product price cannot be empty")
	private BigDecimal price;

	@NotNull(message = "Product stock cannot be empty")
	private Integer stock;

	@Column(name = "enabled")
	private Integer enabled;
}
