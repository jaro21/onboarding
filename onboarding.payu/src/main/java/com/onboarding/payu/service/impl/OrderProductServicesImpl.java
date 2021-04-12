package com.onboarding.payu.service.impl;

import java.util.List;

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

	private final IOrderProductRepository iOrderProductRepository;

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

	@Override public void deleteByIdPurchaseOrder(final Integer idPurchasOrder) {

		iOrderProductRepository.deleteByIdPurchaseOrder(idPurchasOrder);
	}
}
