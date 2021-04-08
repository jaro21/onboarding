package com.onboarding.payu.model.refund.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class RefundDtoRequest {

	@NotNull(message = "Payment Id cannot not be empty")
	private Integer idPayment;
	@NotBlank(message = "Reason for refund cannot not be empty")
	private String reason;
	private Long orderId;
	private String transactionId;
}
