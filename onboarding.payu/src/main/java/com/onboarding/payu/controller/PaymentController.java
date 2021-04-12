package com.onboarding.payu.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.onboarding.payu.model.payment.request.PaymentTransactionRequest;
import com.onboarding.payu.model.payment.response.PaymentWithTokenResponse;
import com.onboarding.payu.model.refund.request.RefundDtoRequest;
import com.onboarding.payu.model.refund.response.RefundDtoResponse;
import com.onboarding.payu.service.IPaymentService;
import com.onboarding.payu.service.IRefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for the Payment's services
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@RequestMapping("/v1.0/payments")
public class PaymentController {

	private IPaymentService iPaymentService;

	private IRefundService iRefundService;

	@Autowired
	public PaymentController(final IPaymentService iPaymentService, final IRefundService iRefundService) {

		this.iPaymentService = iPaymentService;
		this.iRefundService = iRefundService;
	}

	@PostMapping
	public ResponseEntity<PaymentWithTokenResponse> applyPayment(
			@Valid @NotNull @RequestBody final PaymentTransactionRequest paymentTransactionRequest) {

		return ResponseEntity.ok(iPaymentService.paymentWithToken(paymentTransactionRequest));
	}

	@PostMapping("/refund")
	public ResponseEntity<RefundDtoResponse> applyRefund(@Valid @NotNull @RequestBody final RefundDtoRequest refundDtoRequest) {

		return ResponseEntity.ok(iRefundService.appyRefund(refundDtoRequest));
	}
}
