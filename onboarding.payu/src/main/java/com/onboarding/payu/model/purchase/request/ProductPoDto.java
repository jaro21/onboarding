package com.onboarding.payu.model.purchase.request;

import java.math.BigDecimal;
import javax.validation.constraints.NotEmpty;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Builder
@Getter
@ToString
public class ProductPoDto {

	@NotEmpty(message = "Product id cannot not be empty")
	private Integer idProduct;

	@NotEmpty(message = "Product quantity cannot not be empty")
	private Integer quantity;
	private BigDecimal unitValue;
}
