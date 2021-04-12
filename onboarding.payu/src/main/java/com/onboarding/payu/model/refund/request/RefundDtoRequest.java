package com.onboarding.payu.model.refund.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Refund request object
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Builder
@Getter
@ToString
public class RefundDtoRequest {

	@NotNull(message = "Payment Id cannot be empty")
	private Integer idPayment;

	@NotBlank(message = "Reason for refund cannot be empty")
	private String reason;
}
