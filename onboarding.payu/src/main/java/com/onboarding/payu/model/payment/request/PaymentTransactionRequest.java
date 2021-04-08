package com.onboarding.payu.model.payment.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Object for payment transaction's request
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Builder
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PaymentTransactionRequest {

	private Integer idCustomer;
	private Integer idAddress;
	private Integer idCreditCard;
	private Integer idPurchaseOrder;
	private String deviceSessionId;
	private String ipAddress;
	private String cookie;
	private String userAgent;
}
