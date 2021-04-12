package com.onboarding.payu.service.validator;

import java.math.BigDecimal;

import com.onboarding.payu.exception.BusinessAppException;
import com.onboarding.payu.exception.ExceptionCodes;
import com.onboarding.payu.model.StatusType;
import com.onboarding.payu.repository.entity.PurchaseOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PaymentValidator {

	@Value("${payment-api.amount.minimum}")
	private BigDecimal minimumAmount;

	@Value("${payment-api.amount.maximum}")
	private BigDecimal maximumAmount;

	public void runValidations(final PurchaseOrder purchaseOrder) {

		isMinimumAmounValid(purchaseOrder.getValue());
		isMaximumAmounValid(purchaseOrder.getValue());
		isPurchaseOrderStatusValid(purchaseOrder);
	}

	public void isMinimumAmounValid(final BigDecimal value) {

		if (minimumAmount.compareTo(value) >= 0) {
			throw new BusinessAppException(ExceptionCodes.MINIMUM_AMOUNT_INVALID, minimumAmount.toString());
		}
	}

	public void isMaximumAmounValid(final BigDecimal value) {

		if (maximumAmount.compareTo(value) <= 0) {
			throw new BusinessAppException(ExceptionCodes.MAXIMUM_AMOUNT_INVALID, maximumAmount.toString());
		}
	}

	public void isPurchaseOrderStatusValid(final PurchaseOrder purchaseOrder) {

		if (!purchaseOrder.getStatus().equals(StatusType.SAVED.name())) {
			throw new BusinessAppException(ExceptionCodes.PURCHASE_ORDER_STATUS_INVALID);
		}
	}
}
