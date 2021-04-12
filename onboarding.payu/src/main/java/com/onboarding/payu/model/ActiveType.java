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

	DISABLED(0, false),
	ENABLED(1, true);

	private Integer id;
	private boolean name;

	ActiveType(final int id, final boolean name) {

		this.id = id;
		this.name = name;
	}
}
