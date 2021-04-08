package com.onboarding.payu.service.impl;

import java.util.List;

import com.onboarding.payu.exception.BusinessAppException;
import com.onboarding.payu.exception.ExceptionCodes;
import com.onboarding.payu.repository.IOrderProductRepository;
import com.onboarding.payu.repository.entity.OrderProduct;
import com.onboarding.payu.service.IOrderProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IOrderProductService} interface.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class OrderProductServicesImpl implements IOrderProductService {

	private IOrderProductRepository iOrderProductRepository;

	@Autowired
	public OrderProductServicesImpl(final IOrderProductRepository iOrderProductRepository) {

		this.iOrderProductRepository = iOrderProductRepository;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public List<OrderProduct> saveAll(final List<OrderProduct> orderProductList) {

		return iOrderProductRepository.saveAll(orderProductList);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public List<OrderProduct> findByIdPurchaseOrder(final Integer idPurchaseOrder) {

		return iOrderProductRepository.findByIdPurchaseOrder(idPurchaseOrder).orElseThrow(
				() -> new BusinessAppException(ExceptionCodes.PURCHASE_ORDER_INVALID));
	}
}
