package com.onboarding.payu.security;

import static java.lang.String.format;

import java.util.ArrayList;

import com.onboarding.payu.exception.ExceptionCodes;
import com.onboarding.payu.repository.entity.Customer;
import com.onboarding.payu.service.ICustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JwtUserDetailsService implements UserDetailsService {

	private ICustomerService iCustomerService;

	@Autowired
	public JwtUserDetailsService(final ICustomerService iCustomerService) {

		this.iCustomerService = iCustomerService;
	}

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

		try {
			final Customer customer = iCustomerService.findByUsername(username);
			return User.builder().username(username).password(customer.getPassword_hash()).authorities(new ArrayList<>()).build();
		}catch (Exception e){
			log.debug("loadUserByUsername(username={})) ", username);
			throw new UsernameNotFoundException(format(ExceptionCodes.USERNAME_NOT_EXIST.getMessage(), username));
		}
	}
}
