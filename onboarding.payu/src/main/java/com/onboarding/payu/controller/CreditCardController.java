package com.onboarding.payu.controller;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.onboarding.payu.model.tokenization.request.CreditCardRequest;
import com.onboarding.payu.model.tokenization.response.TokenResponse;
import com.onboarding.payu.repository.entity.CreditCard;
import com.onboarding.payu.service.ICreditCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for the Credit Card's services
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@RequestMapping("/v1.0/credit-cards")
public class CreditCardController {

	private ICreditCard iCreditCard;

	@Autowired
	public CreditCardController(final ICreditCard iCreditCard) {

		this.iCreditCard = iCreditCard;
	}

	@PostMapping
	public TokenResponse tokenizationCard(@Valid @NotNull @RequestBody final CreditCardRequest creditCardRequest) {

		return iCreditCard.tokenizationCard(creditCardRequest);
	}

	@GetMapping("/{dni}")
	public ResponseEntity<List<CreditCard>> findAllCardsByCustomer(@NotNull @PathVariable String dni) {

		return ResponseEntity.ok(iCreditCard.findAllCardsByCustomer(dni));
	}
}
