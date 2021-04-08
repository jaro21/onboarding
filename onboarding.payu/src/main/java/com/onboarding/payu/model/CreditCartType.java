package com.onboarding.payu.model;

import lombok.Getter;

/**
 * Enumeration with credit card's types, used to Payment
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
public enum CreditCartType {
	VISA("VISA","^(4)(\\d{12}|\\d{15})$|^(606374\\d{10}$)"),
	DINERS("DINERS","(^[35](?:0[0-5]|[68][0-9])[0-9]{11}$)|(^30[0-5]{11}$)|(^3095(\\d{10})$)|(^36{12}$)|(^3[89](\\d{12})$)"),
	DISCOVER("DISCOVER","^(6011\\d{12})$|^(64[4-9]\\{13})$|^(65\\d{14})$"),
	AMEX("AMEX","^(3[47]\\d{13})$"),
	MASTERCARD("MASTERCARD","^(5[1-5]\\d{14}$)|^(2(?:2(?:2[1-9]|[3-9]||d)|[3-6]\\d\\d|7(?:[01]\\d|20))\\d{12}$)"),
	NARANJA("NARANJA","^(589562)\\d{10}$"),
	CODENSA("CODENSA","^590712(\\d{10})$");

	private String nameCard;
	private String pattern;

	CreditCartType(final String nameCard, final String pattern) {
		this.nameCard = nameCard;
		this.pattern = pattern;
	}
}
