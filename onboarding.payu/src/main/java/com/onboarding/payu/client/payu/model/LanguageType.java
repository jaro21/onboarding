package com.onboarding.payu.client.payu.model;

import lombok.Getter;

/**
 * Enumeration with language types, used by PayU Provider
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
public enum LanguageType {

	ES("es"),
	EN("en");

	private String language;

	LanguageType(final String language) {
		this.language = language;
	}
}
