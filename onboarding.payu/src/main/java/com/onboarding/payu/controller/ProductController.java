package com.onboarding.payu.controller;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.onboarding.payu.model.product.request.ProductRequest;
import com.onboarding.payu.model.product.response.ProductResponse;
import com.onboarding.payu.repository.entity.Product;
import com.onboarding.payu.service.IProductService;
import lombok.extern.slf4j.Slf4j;
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
 * Controller for the Product's services
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/v1.0/products")
public class ProductController {

	private IProductService iProductService;

	@Autowired
	public ProductController(final IProductService iProductService) {

		this.iProductService = iProductService;
	}

	@PostMapping
	public ResponseEntity<ProductResponse> addProduct(@Valid @NotNull @RequestBody ProductRequest productRequest) {

		return new ResponseEntity<>(iProductService.saveProduct(productRequest), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<ProductResponse>> findAllProducts() {

		return ResponseEntity.ok(iProductService.findProducts());
	}

	@GetMapping("/active")
	public ResponseEntity<List<ProductResponse>> findByActive() {

		return ResponseEntity.ok(iProductService.findByActive());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductResponse> findProductById(@NotNull @PathVariable Integer id) {

		Validate.notNull(id, "Product identification cannot not be empty");
		return ResponseEntity.ok(iProductService.findProductById(id));
	}

	@PutMapping
	public ResponseEntity updateProduct(@Valid @NotNull @RequestBody ProductRequest productRequest) {

		Validate.notNull(productRequest.getIdProduct(), "Product identification cannot not be empty");
		iProductService.updateProduct(productRequest);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity deleteProduct(@NotNull @PathVariable Integer id) {

		Validate.notNull(id, "Product identification cannot not be empty to remove");
		iProductService.deleteProduct(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
