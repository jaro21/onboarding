package com.onboarding.payu.service;

import com.onboarding.payu.model.authentication.request.AuthenticationRequest;
import com.onboarding.payu.model.authentication.response.AuthenticationResponse;

/**
 * Interface that define of Authentication's services
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IAuthentication {

	AuthenticationResponse createAuthenticationToken(AuthenticationRequest authenticationRequest);

}
