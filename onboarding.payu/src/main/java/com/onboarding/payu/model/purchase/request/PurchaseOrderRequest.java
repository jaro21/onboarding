package com.onboarding.payu.model.purchase.request;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Object for purchase order's request
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Builder
@Getter
@ToString
public class PurchaseOrderRequest {

	private Integer id;

	@NotNull(message = "Customer identification cannot be empty")
	private CustomerPoRequest customer;

	@NotEmpty(message = "Product cannot be empty")
	private List<ProductPoDto> productList;
}
