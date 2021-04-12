package com.onboarding.payu.controller;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.onboarding.payu.model.purchase.request.DeclineRequest;
import com.onboarding.payu.model.purchase.request.PurchaseOrderRequest;
import com.onboarding.payu.model.purchase.response.PurchaseOrderResponse;
import com.onboarding.payu.service.IPurchaseOrder;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@PutMapping
	public ResponseEntity<PurchaseOrderResponse> updatePurchaseOrder(
			@Valid @NotNull @RequestBody final PurchaseOrderRequest purchaseOrderRequest) {

		Validate.notNull(purchaseOrderRequest.getId(), "Purchase order identification cannot be empty");
		return new ResponseEntity<>(iPurchaseOrder.updatePurchaseOrder(purchaseOrderRequest), HttpStatus.CREATED);
	}


	@PostMapping("/decline")
	public ResponseEntity decline(@Valid @NotNull @RequestBody final DeclineRequest declineRequest){

		iPurchaseOrder.decline(declineRequest);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/customer-id/{idCustomer}")
	public ResponseEntity<List<PurchaseOrderResponse>> getAllPurchaseOrderForCustomer(@NotNull @PathVariable Integer idCustomer) {

		return ResponseEntity.ok(iPurchaseOrder.findByIdCustomer(idCustomer));
	}

	@GetMapping("/customer-id/{idCustomer}/order-id/{idPurchaseOrder}")
	public ResponseEntity<PurchaseOrderResponse> getPurchaseOrderForCustomer(@NotNull @PathVariable Integer idCustomer,
															   @NotNull @PathVariable Integer idPurchaseOrder) {

		return ResponseEntity.ok(iPurchaseOrder.findByIdCustomerAndIdPurchaseOrder(idCustomer, idPurchaseOrder));
	}
}
