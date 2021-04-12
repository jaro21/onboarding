package com.onboarding.payu.security;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -2550185165626007488L;

	@Value("${security.validity.time}")
	private Long token_validity;

	@Value("${security.secret}")
	private String secret;

	/**
	 * retrieve username from jwt token
	 *
	 * @param token {@link String}
	 * @return {@link String}
	 */
	public String getUsernameFromToken(final String token) {

		return getClaimFromToken(token, Claims::getSubject);
	}

	/**
	 * retrieve expiration date from jwt token
	 *
	 * @param token {@link String}
	 * @return {@link Date}
	 */
	public Date getExpirationDateFromToken(final String token) {

		return getClaimFromToken(token, Claims::getExpiration);
	}

	/**
	 * @param token          {@link String}
	 * @param claimsResolver {@link Function}
	 * @param <T>
	 * @return
	 */
	public <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver) {

		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * for retrieveing any information from token we will need the secret key
	 *
	 * @param token {@link String}
	 * @return {@link Claims}
	 */
	private Claims getAllClaimsFromToken(final String token) {

		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	/**
	 * check if the token has expired
	 *
	 * @param token {@link String}
	 * @return {@link Boolean}
	 */
	private Boolean isTokenExpired(final String token) {

		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	/**
	 * generate token for user
	 *
	 * @param userDetails {@link UserDetails}
	 * @return {@link String}
	 */
	public String generateToken(final UserDetails userDetails) {

		final Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}

	/**
	 * Creating the token with claims
	 *
	 * @param claims  {@link Map<String,Object>}
	 * @param subject {@link String}
	 * @return {@link String}
	 */
	private String doGenerateToken(final Map<String, Object> claims, final String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				   .setExpiration(new Date(System.currentTimeMillis() + token_validity))
				   .signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	/**
	 * validate token
	 *
	 * @param token       {@link String}
	 * @param userDetails {@link UserDetails}
	 * @return {@link Boolean}
	 */
	public Boolean validateToken(final String token, final UserDetails userDetails) {

		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
