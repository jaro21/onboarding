package com.onboarding.payu.client.payu.model;

import lombok.Getter;

/**
 * Enumeration with country types, used by PayU Provider
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
public enum CountryType {

	COLOMBIA("CO");

	private String country;

	CountryType(final String country) {

		this.country = country;
	}
}
