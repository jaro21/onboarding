package com.onboarding.payu.service.impl.mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import com.onboarding.payu.exception.BusinessAppException;
import com.onboarding.payu.exception.ExceptionCodes;
import com.onboarding.payu.model.StatusType;
import com.onboarding.payu.model.purchase.request.ProductPoDto;
import com.onboarding.payu.model.purchase.request.PurchaseOrderRequest;
import com.onboarding.payu.model.purchase.response.ProductPurchaseResponse;
import com.onboarding.payu.model.purchase.response.PurchaseOrderResponse;
import com.onboarding.payu.repository.entity.Customer;
import com.onboarding.payu.repository.entity.OrderProduct;
import com.onboarding.payu.repository.entity.Product;
import com.onboarding.payu.repository.entity.PurchaseOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PurchaseOrderMapper {

	/**
	 * Get PurchaseOrder to register them in database
	 *
	 * @param customer             {@link Customer}
	 * @param productList          {@link List < Product >}
	 * @param purchaseOrderRequest {@link PurchaseOrder}
	 * @return {@link PurchaseOrder}
	 */
	public PurchaseOrder toPurchaseOrder(final Customer customer, final List<Product> productList,
										 final PurchaseOrderRequest purchaseOrderRequest) {

		return PurchaseOrder.builder().customer(customer)
							.status(StatusType.SAVED.name())
							.date(LocalDate.now())
							.value(getTotalValue(productList, purchaseOrderRequest.getProductList()))
							.referenceCode(UUID.randomUUID().toString())
							.build();
	}

	/**
	 * Get PurchaseOrder to register them in database
	 *
	 * @param purchaseOrder        {@link PurchaseOrder}
	 * @param productList          {@link List < Product >}
	 * @param purchaseOrderRequest {@link PurchaseOrder}
	 * @return {@link PurchaseOrder}
	 */
	public PurchaseOrder toPurchaseOrder(final PurchaseOrder purchaseOrder, final List<Product> productList,
										 final PurchaseOrderRequest purchaseOrderRequest) {

		return PurchaseOrder.builder().idPurchaseOrder(purchaseOrder.getIdPurchaseOrder())
							.customer(purchaseOrder.getCustomer())
							.status(StatusType.SAVED.name())
							.date(LocalDate.now())
							.value(getTotalValue(productList, purchaseOrderRequest.getProductList()))
							.referenceCode(UUID.randomUUID().toString())
							.build();
	}

	/**
	 * Get the total value of the purchase order
	 *
	 * @param productList      {@link List<Product>}
	 * @param productPoDTOList {@link List<ProductPoDto>}
	 * @return {@link BigDecimal}
	 */
	private BigDecimal getTotalValue(final List<Product> productList, final List<ProductPoDto> productPoDTOList) {

		try {
			return productPoDTOList.stream().map(productPoDTO -> BigDecimal.valueOf(productPoDTO.getQuantity()).multiply(
					productList.stream().filter(product -> product.getIdProduct().equals(productPoDTO.getIdProduct())).findFirst().get()
							   .getPrice())).reduce(BigDecimal.valueOf(0.0), (a, b) -> a.add(b));
		} catch (NoSuchElementException e) {
			log.debug("Invalid product in list [{}]", productPoDTOList.toString(), e);
			throw new BusinessAppException(ExceptionCodes.ERROR_TO_PROCESS_PRODUCT);
		}
	}

	/**
	 * @param purchaseOrder {@link PurchaseOrder}
	 * @return {@link PurchaseOrderResponse}
	 */
	public PurchaseOrderResponse toPurchaseOrderResponse(final PurchaseOrder purchaseOrder) {

		return PurchaseOrderResponse.builder().id(purchaseOrder.getIdPurchaseOrder())
									.status(purchaseOrder.getStatus())
									.referenceCode(purchaseOrder.getReferenceCode())
									.date(purchaseOrder.getDate())
									.value(purchaseOrder.getValue()).build();
	}

	/**
	 * @param purchaseOrder {@link PurchaseOrder}
	 * @param products      {@link List<Product>}
	 * @return {@link PurchaseOrderResponse}
	 */
	public PurchaseOrderResponse toPurchaseOrderResponse(final PurchaseOrder purchaseOrder, final List<Product> products) {

		final PurchaseOrderResponse.PurchaseOrderResponseBuilder purchaseOrderResponseBuilder =
				PurchaseOrderResponse.builder().id(purchaseOrder.getIdPurchaseOrder())
									 .status(purchaseOrder.getStatus())
									 .referenceCode(purchaseOrder.getReferenceCode())
									 .date(purchaseOrder.getDate())
									 .value(purchaseOrder.getValue());

		toProductPurchaseResponseList(purchaseOrderResponseBuilder, products);

		return purchaseOrderResponseBuilder.build();
	}

	private void toProductPurchaseResponseList(final PurchaseOrderResponse.PurchaseOrderResponseBuilder purchaseOrderResponseBuilder,
											   final List<Product> products) {

		if (products != null && !products.isEmpty()) {
			purchaseOrderResponseBuilder
					.products(products.stream().map(this::toProductPurchaseResponse).collect(Collectors.toList()));
		}
	}

	private ProductPurchaseResponse toProductPurchaseResponse(final Product product) {

		return ProductPurchaseResponse.builder()
									  .idProduct(product.getIdProduct())
									  .name(product.getName())
									  .code(product.getCode())
									  .description(product.getDescription())
									  .price(product.getPrice())
									  .build();
	}

	public PurchaseOrderResponse toPurchaseOrderResponseWithProducts(final PurchaseOrder purchaseOrder) {

		return PurchaseOrderResponse.builder().id(purchaseOrder.getIdPurchaseOrder())
									.status(purchaseOrder.getStatus())
									.referenceCode(purchaseOrder.getReferenceCode())
									.date(purchaseOrder.getDate())
									.value(purchaseOrder.getValue())
									.products(buildProductPurchaseResponseList(purchaseOrder.getProducts()))
									.build();
	}

	private List<ProductPurchaseResponse> buildProductPurchaseResponseList(final List<OrderProduct> products) {

		return products.stream().map(this::buildProductPurchaseResponse).collect(Collectors.toList());
	}

	private ProductPurchaseResponse buildProductPurchaseResponse(final OrderProduct orderProduct) {

		return ProductPurchaseResponse.builder()
									  .idProduct(orderProduct.getProduct().getIdProduct())
									  .code(orderProduct.getProduct().getCode())
									  .description(orderProduct.getProduct().getDescription())
									  .price(orderProduct.getUnitValue())
									  .quantity(orderProduct.getQuantity())
									  .build();
	}
}
