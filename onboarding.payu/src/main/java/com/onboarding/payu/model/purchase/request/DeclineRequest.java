package com.onboarding.payu.model.purchase.request;

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
public class DeclineRequest {

	@NotNull(message = "Purchase order identification cannot not be empty")
	private Integer idPurchaseOrder;

	@NotNull(message = "Customer identification cannot not be empty")
	private Integer idCustomer;
}
