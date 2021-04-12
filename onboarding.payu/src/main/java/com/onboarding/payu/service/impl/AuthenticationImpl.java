package com.onboarding.payu.service.impl;

import com.onboarding.payu.model.authentication.request.AuthenticationRequest;
import com.onboarding.payu.model.authentication.response.AuthenticationResponse;
import com.onboarding.payu.security.JwtTokenUtil;
import com.onboarding.payu.security.JwtUserDetailsService;
import com.onboarding.payu.service.IAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IAuthentication} interface.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Service
public class AuthenticationImpl implements IAuthentication {

	private AuthenticationManager authenticationManager;

	private JwtTokenUtil jwtTokenUtil;

	private JwtUserDetailsService userDetailsService;

	@Autowired
	public AuthenticationImpl(final AuthenticationManager authenticationManager,
							  final JwtTokenUtil jwtTokenUtil, final JwtUserDetailsService userDetailsService) {

		this.authenticationManager = authenticationManager;
		this.jwtTokenUtil = jwtTokenUtil;
		this.userDetailsService = userDetailsService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public AuthenticationResponse createAuthenticationToken(final AuthenticationRequest authenticationRequest) {

		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		return AuthenticationResponse.builder().token(jwtTokenUtil.generateToken(userDetails)).build();
	}
}
