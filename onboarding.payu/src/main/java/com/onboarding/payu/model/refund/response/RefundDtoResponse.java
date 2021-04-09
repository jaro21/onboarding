package com.onboarding.payu.model.refund.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RefundDtoResponse {

	private String code;
	private String error;
	private TransactionDtoResponse transactionDtoResponse;
}
