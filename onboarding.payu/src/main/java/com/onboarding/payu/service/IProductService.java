package com.onboarding.payu.service;

import java.util.List;

import com.onboarding.payu.model.product.request.ProductRequest;
import com.onboarding.payu.model.product.response.ProductResponse;
import com.onboarding.payu.repository.entity.Product;

/**
 * Interface that define of Product's services
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IProductService {

	/**
	 * Method to create product
	 *
	 * @param product {@link ProductRequest}
	 * @return {@link ProductResponse}
	 */
	ProductResponse saveProduct(ProductRequest product);

	/**
	 * Method to get all products
	 *
	 * @return {@link List<ProductResponse>}
	 */
	List<ProductResponse> findProducts();

	/**
	 * Method to get all products
	 *
	 * @return {@link List<Product>}
	 */
	List<ProductResponse> findByEnabled();

	/**
	 * Method to get all products by ids
	 *
	 * @param ids {@link List<Integer>}
	 * @return {@link List<Product>}
	 */
	List<Product> findProductsByIds(List<Integer> ids);

	/**
	 * Method to get one product by id
	 *
	 * @param id {@link Integer}
	 * @return {@link Product}
	 */
	ProductResponse findProductById(Integer id);

	/**
	 * Method to delete one product by id
	 *
	 * @param id {@link Integer}
	 */
	void deleteProduct(Integer id);

	/**
	 * Method to update product
	 *
	 * @param product {@link ProductRequest}
	 * @return {@link Product}
	 */
	void updateProduct(ProductRequest product);

	/**
	 * Method to update product
	 *
	 * @param product {@link Product}
	 * @return {@link Product}
	 */
	Product updateProduct(Product product);

	/**
	 * Update product stock by id
	 *
	 * @param stock {@link Integer}
	 * @param id    {@link Integer}
	 * @return {@link Integer}
	 */
	Integer updateStockById(Integer stock, Integer id);
}
