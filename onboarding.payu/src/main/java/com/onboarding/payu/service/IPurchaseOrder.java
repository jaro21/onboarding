package com.onboarding.payu.service;

import com.onboarding.payu.model.purchase.request.PurchaseOrderRequest;
import com.onboarding.payu.model.purchase.response.PurchaseOrderResponse;
import com.onboarding.payu.repository.entity.PurchaseOrder;

/**
 * Interface that define of Purchase Order's services
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IPurchaseOrder {

	/**
	 * Service to save a purchase order
	 *
	 * @param purchaseOrder {@link PurchaseOrderRequest}
	 * @return {@link PurchaseOrderResponse}
	 * @
	 */
	PurchaseOrderResponse addPurchaseOrder(PurchaseOrderRequest purchaseOrder);

	/**
	 * Method to get Purchase Order by id
	 *
	 * @param idPurchaseOrder {@link Integer}
	 * @return {@link PurchaseOrder}
	 * @
	 */
	PurchaseOrder findById(Integer idPurchaseOrder);

	/**
	 * Update Purchase Order's status by id
	 *
	 * @param status {@link String}
	 * @param id     {@link Integer}
	 * @return {@link Integer}
	 */
	Integer updateStatusById(String status, Integer id);

	/**
	 * Update Purchase Order
	 *
	 * @param purchaseOrder {@link PurchaseOrder}
	 * @return {@link PurchaseOrder}
	 */
	PurchaseOrder update(PurchaseOrder purchaseOrder);

	/**
	 * Decline Purchase Order by id
	 *
	 * @param id {@link Integer}
	 */
	void decline(Integer id);
}
