package com.onboarding.payu.client.payu.model;

/**
 * Enumeration with transaction types, used by PayU Provider
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public enum TransactionType {

	AUTHORIZATION_AND_CAPTURE,
	AUTHORIZATION,
	REFUND
}
