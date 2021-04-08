package com.onboarding.payu.model.purchase.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

/**
 * Object for purchase order's response
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PurchaseOrderResponse {

	private Integer id;
	private String status;
	private String referenceCode;
	private LocalDate date;
	private BigDecimal value;
}
