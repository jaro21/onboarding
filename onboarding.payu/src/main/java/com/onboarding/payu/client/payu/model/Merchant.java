package com.onboarding.payu.client.payu.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Object with merchant information for payments-api's request
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@Builder
@ToString
public class Merchant {

	private String apiKey;
	private String apiLogin;
}
