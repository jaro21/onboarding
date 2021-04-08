package com.onboarding.payu.client.payu.model;

/**
 * Enumeration with command types, used by PayU Provider
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public enum CommanType {

	CREATE_TOKEN,
	SUBMIT_TRANSACTION,
	ORDER_DETAIL;
}
