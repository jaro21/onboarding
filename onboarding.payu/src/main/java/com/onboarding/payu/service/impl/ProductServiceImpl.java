package com.onboarding.payu.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import com.onboarding.payu.exception.BusinessAppException;
import com.onboarding.payu.exception.ExceptionCodes;
import com.onboarding.payu.model.product.request.ProductRequest;
import com.onboarding.payu.repository.IProductRepository;
import com.onboarding.payu.repository.entity.Product;
import com.onboarding.payu.service.IProductService;
import com.onboarding.payu.service.impl.mapper.ProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IProductService} interface.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Service
public class ProductServiceImpl implements IProductService {

	private IProductRepository iProductRepository;

	private ProductMapper productMapper;

	@Autowired
	public ProductServiceImpl(final IProductRepository iProductRepository,
							  final ProductMapper productMapper) {

		this.iProductRepository = iProductRepository;
		this.productMapper = productMapper;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public Product saveProduct(final ProductRequest product) {

		log.debug("saveProduct : ", product.toString());
		productCreateValidation(product);
		return iProductRepository.save(productMapper.toProduct(product));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public List<Product> findProducts() {

		return iProductRepository.findAll();
	}

	@Override public List<Product> findByActive() {

		return iProductRepository.findByActive(1).orElse(Collections.emptyList());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public List<Product> findProductsByIds(final List<Integer> ids) {

		return iProductRepository.findAllById(ids);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public Product findProductById(final Integer id) {

		return iProductRepository.findById(id).orElseThrow(
				() -> new BusinessAppException(ExceptionCodes.PRODUCT_ID_NOT_EXIST, id.toString()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public void deleteProduct(final Integer id) {

		try {
			findProductById(id);
			iProductRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			log.error("Failed to delete product id {} ", id, e);
			throw new BusinessAppException(ExceptionCodes.ERROR_TO_DELETE_PRODUCT);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public Product updateProduct(final ProductRequest product) {

		log.debug("updateProduct : ", product.toString());
		productValidation(product);
		return updateProduct(productMapper.toProduct(product));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public Product updateProduct(final Product product) {

		log.debug("updateProduct : ", product.toString());
		findProductById(product.getIdProduct());
		return iProductRepository.save(product);
	}

	@Override public Integer updateStockById(final Integer stock, final Integer id) {

		log.debug("updateStockById(stock = {}, Id = {}) :", stock, id);
		return iProductRepository.updateStockById(stock, id);
	}

	/**
	 * Run validations on the product to create
	 *
	 * @param product {@link ProductRequest}
	 */
	private void productCreateValidation(final ProductRequest product) {

		if (iProductRepository.findByCode(product.getCode()).isPresent()) {
			throw new BusinessAppException(ExceptionCodes.DUPLICATE_PRODUCT_CODE, product.getCode());
		}

		productValidation(product);
	}

	/**
	 * Run validations on the product to create or update
	 *
	 * @param product {@link ProductRequest}
	 */
	private void productValidation(final ProductRequest product) {

		if (product.getStock() < 0) {
			throw new BusinessAppException(ExceptionCodes.PRODUCT_STOCK_INVALID);
		}
		if (product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
			throw new BusinessAppException(ExceptionCodes.PRODUCT_PRICE_INVALID);
		}
	}
}
