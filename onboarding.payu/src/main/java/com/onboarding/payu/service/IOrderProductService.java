package com.onboarding.payu.service;

import java.util.List;

import com.onboarding.payu.repository.entity.OrderProduct;

/**
 * Interface that define of Order Product's services
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IOrderProductService {

	/**
	 * Save the list of products included in a purchase order
	 *
	 * @param orderProductList {@link List<OrderProduct>}
	 * @return {@link List<OrderProduct>}
	 */
	List<OrderProduct> saveAll(List<OrderProduct> orderProductList);

	/**
	 * Get purchase order list by idPurchaseOrder
	 *
	 * @param idPurchaseOrder {@link Integer}
	 * @return {@link List<OrderProduct>}
	 */
	List<OrderProduct> findByIdPurchaseOrder(Integer idPurchaseOrder);
}
