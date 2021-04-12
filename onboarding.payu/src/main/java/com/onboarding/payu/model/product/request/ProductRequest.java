package com.onboarding.payu.model.product.request;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Object for product's request
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Builder
@Getter
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProductRequest {

	private Integer idProduct;

	@NotBlank(message = "Product name cannot be empty")
	@Size(max = 25, message = "The size of the name must be a maximum of 25 characters.")
	private String name;

	@NotBlank(message = "Product code cannot be empty")
	@Size(max = 10, message = "The size of the code must be a maximum of 10 characters.")
	private String code;

	@NotBlank(message = "Product description cannot be empty")
	@Size(max = 50, message = "The size of the description must be a maximum of 50 characters.")
	private String description;

	@NotNull(message = "Product price cannot be empty")
	private BigDecimal price;

	@NotNull(message = "Product stock cannot be empty")
	private Integer stock;

	@Builder.Default
	private boolean enabled = true;
}
