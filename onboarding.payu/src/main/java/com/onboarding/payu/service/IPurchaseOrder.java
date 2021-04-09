package com.onboarding.payu.service;

import java.util.List;

import com.onboarding.payu.model.purchase.request.DeclineRequest;
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
	PurchaseOrder findByIdPurchaseOrder(Integer idPurchaseOrder);

	/**
	 * Update Purchase Order's status by id
	 *
	 * @param status {@link String}
	 * @param id     {@link Integer}
	 */
	void updateStatusById(String status, Integer id);

	/**
	 * Decline Purchase Order by id
	 *
	 * @param declineRequest {@link DeclineRequest}
	 */
	void decline(DeclineRequest declineRequest);

	/**
	 * Method to get Purchase Order by idCustomer and idPurchaseOrder
	 *
	 * @param idCustomer {@link Integer}
	 * @param idPurchaseOrder {@link Integer}
	 * @return {@link PurchaseOrderResponse}
	 */
	PurchaseOrderResponse findByIdCustomerAndIdPurchaseOrder(Integer idCustomer, Integer idPurchaseOrder);

	/**
	 * Method to get Purchase Order by idCustomer and idPurchaseOrder
	 *
	 * @param idCustomer {@link Integer}
	 * @return {@link List<PurchaseOrderResponse>}
	 */
	List<PurchaseOrderResponse> findByIdCustomer(Integer idCustomer);

	/**
	 *
	 *
	 * @param purchaseOrderRequest {@link PurchaseOrderResponse}
	 * @return {@link PurchaseOrderResponse}
	 */
	PurchaseOrderResponse updatePurchaseOrder(PurchaseOrderRequest purchaseOrderRequest);
}
