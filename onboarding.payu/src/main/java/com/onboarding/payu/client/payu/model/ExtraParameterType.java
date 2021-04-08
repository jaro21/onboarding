package com.onboarding.payu.client.payu.model;

import lombok.Getter;

/**
 * Enumeration with extra parameter types, used by PayU Provider
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
public enum ExtraParameterType {

	INSTALLMENTS_NUMBER(1);

	final int id;

	ExtraParameterType(final int id) {
		this.id = id;
	}
}
