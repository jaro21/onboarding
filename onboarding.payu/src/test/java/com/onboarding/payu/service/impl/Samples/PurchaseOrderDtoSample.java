package com.onboarding.payu.service.impl.Samples;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.onboarding.payu.model.StatusType;
import com.onboarding.payu.model.purchase.request.PurchaseOrderRequest;
import com.onboarding.payu.model.purchase.response.PurchaseOrderResponse;
import com.onboarding.payu.repository.entity.PurchaseOrder;

/**
 * Get sample object for run unit tests.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public class PurchaseOrderDtoSample {

	public static PurchaseOrderRequest getPurchasOrderDto() {

		return PurchaseOrderRequest.builder()
								   .customer(CustomerSample.getCustomerDto())
								   .productList(ProductSample.getProductDtoList()).build();
	}

	public static PurchaseOrderRequest getPurchasOrderDtoStockInvalid() {

		return PurchaseOrderRequest.builder()
								   .customer(CustomerSample.getCustomerDto())
								   .productList(ProductSample.getProductDtoListStockInvalid()).build();
	}

	public static PurchaseOrderResponse getPurchaseOrderResponse() {

		return PurchaseOrderResponse.builder()
									.id(1)
									.status(StatusType.SAVED.name())
									.referenceCode("cc82a58e-cfd3-4198-9ae4-d350dc3f7498")
									.date(LocalDate.now())
									.value(BigDecimal.valueOf(1055000L)).build();
	}

	public static PurchaseOrder getPurchaseOrder() {

		return PurchaseOrder.builder()
							.idPurchaseOrder(1)
							.customer(CustomerSample.getCustomer())
							.status(StatusType.SAVED.name())
							.referenceCode("cc82a58e-cfd3-4198-9ae4-d350dc3f7498")
							.value(BigDecimal.valueOf(1055000L))
							.build();
	}
}
