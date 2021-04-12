package com.onboarding.payu.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.onboarding.payu.model.customer.request.CustomerRequest;
import com.onboarding.payu.model.customer.response.CustomerResponse;
import com.onboarding.payu.service.ICustomerService;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@RequestMapping("/v1.0/customers")
public class CustomerController {

	private ICustomerService iCustomerService;

	@Autowired
	public CustomerController(final ICustomerService iCustomerService) {

		this.iCustomerService = iCustomerService;
	}

	@PostMapping
	public ResponseEntity<CustomerResponse> createCustomer(@Valid @NotNull @RequestBody final CustomerRequest customerRequest){
		return new ResponseEntity<>(iCustomerService.save(customerRequest), HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CustomerResponse> findCustomerById(@NotNull @PathVariable Integer id) {

		Validate.notNull(id, "Customer id cannot be empty");
		return ResponseEntity.ok(iCustomerService.findCustomerById(id));
	}

	@PutMapping
	public ResponseEntity<CustomerResponse> updateCustomer(@Valid @NotNull @RequestBody CustomerRequest customerRequest) {

		Validate.notNull(customerRequest.getIdCustomer(), "Client id cannot be empty");
		return ResponseEntity.ok(iCustomerService.update(customerRequest));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<CustomerResponse> deleteCustomer(@NotNull @PathVariable Integer id) {

		Validate.notNull(id, "Product identification cannot be empty to remove");
		iCustomerService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
