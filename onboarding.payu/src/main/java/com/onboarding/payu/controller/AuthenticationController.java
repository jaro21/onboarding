package com.onboarding.payu.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.onboarding.payu.model.authentication.request.AuthenticationRequest;
import com.onboarding.payu.model.authentication.response.AuthenticationResponse;
import com.onboarding.payu.service.IAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for the Credit Card's services
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@CrossOrigin
public class AuthenticationController {

	private IAuthentication iAuthentication;

	@Autowired
	public AuthenticationController(final IAuthentication iAuthentication) {

		this.iAuthentication = iAuthentication;
	}

	@RequestMapping(value = "/v1.0/authenticate", method = RequestMethod.POST)
	public ResponseEntity<AuthenticationResponse> createAuthenticationToken(
			@Valid @NotNull @RequestBody final AuthenticationRequest authenticationRequest) {

		return ResponseEntity.ok(iAuthentication.createAuthenticationToken(authenticationRequest));
	}
}
