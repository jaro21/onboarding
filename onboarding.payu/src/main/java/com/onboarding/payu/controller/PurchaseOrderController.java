package com.onboarding.payu.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.onboarding.payu.model.purchase.request.PurchaseOrderRequest;
import com.onboarding.payu.model.purchase.response.PurchaseOrderResponse;
import com.onboarding.payu.service.IPurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for the Purchase Order's services
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@RequestMapping("/v1.0/purchase-orders")
public class PurchaseOrderController {

	private IPurchaseOrder iPurchaseOrder;

	@Autowired
	public PurchaseOrderController(final IPurchaseOrder iPurchaseOrder) {

		this.iPurchaseOrder = iPurchaseOrder;
	}

	@PostMapping
	public ResponseEntity<PurchaseOrderResponse> addPurchaseOrder(
			@Valid @NotNull @RequestBody final PurchaseOrderRequest purchaseOrderRequest) {

		return new ResponseEntity<>(iPurchaseOrder.addPurchaseOrder(purchaseOrderRequest), HttpStatus.CREATED);
	}

	@PostMapping
	public ResponseEntity decline(@Valid @NotNull @RequestBody final Integer id){

		iPurchaseOrder.decline(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
