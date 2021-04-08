package com.onboarding.payu.model;

import lombok.Getter;

/**
 * Enumeration with active's types
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
public enum ActiveType {
	INACTIVE(0, "INACTIVE"),
	ACTIVE(1, "ACTIVE");

	private int id;
	private String name;

	ActiveType(final int id, final String name) {
		this.id = id;
		this.name = name;
	}
}
