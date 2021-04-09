package com.onboarding.payu.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.onboarding.payu.exception.BusinessAppException;
import com.onboarding.payu.exception.ExceptionCodes;
import com.onboarding.payu.model.product.request.ProductRequest;
import com.onboarding.payu.model.product.response.ProductResponse;
import com.onboarding.payu.repository.IProductRepository;
import com.onboarding.payu.repository.entity.Product;
import com.onboarding.payu.service.impl.Samples.ProductSample;
import com.onboarding.payu.service.impl.mapper.ProductMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit testing for product implementation.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

	@Mock
	private IProductRepository iProductRepositoryMock;

	@Mock
	private ProductMapper productMapper;

	@InjectMocks
	private ProductServiceImpl productServiceImpl;

	@Test
	void saveProductSuccessful() {

		final ProductRequest productRequestSample = ProductSample.getProductDto();
		final Product product = ProductSample.getProduct();
		when(iProductRepositoryMock.findByCode(any(String.class))).thenReturn(Optional.empty());
		when(iProductRepositoryMock.save(any(Product.class))).thenReturn(product);
		when(productMapper.toProduct(any(ProductRequest.class))).thenReturn(product);

		final ProductResponse productRes = productServiceImpl.saveProduct(productRequestSample);

		verify(iProductRepositoryMock).findByCode(any(String.class));
		verify(iProductRepositoryMock).save(any(Product.class));

		//assertEquals(productRequestSample.getIdProduct(), productRes.getIdProduct());
		//assertEquals(productRequestSample.getCode(), productRes.getCode());
	}

	@Test
	void saveProduct_InvalidCode() {

		final ProductRequest productRequestSample = ProductSample.getProductDto();
		when(iProductRepositoryMock.findByCode(any(String.class))).thenReturn(Optional.of(ProductSample.getProduct()));

		try {
			final ProductResponse productRes = productServiceImpl.saveProduct(productRequestSample);
			fail();
		} catch (BusinessAppException e) {
			assertEquals(e.getCode(), ExceptionCodes.DUPLICATE_PRODUCT_CODE.getCode());
		}

		verify(iProductRepositoryMock).findByCode(any(String.class));
		verify(iProductRepositoryMock, times(0)).save(any(Product.class));
	}

	@Test
	void saveProduct_StockLessThanZero() {

		final ProductRequest productRequestSample = ProductSample.getProductDtoStockLessThanZero();
		when(iProductRepositoryMock.findByCode(any(String.class))).thenReturn(Optional.empty());

		try {
			final ProductResponse productRes = productServiceImpl.saveProduct(productRequestSample);
			fail();
		} catch (BusinessAppException e) {
			assertEquals(e.getCode(), ExceptionCodes.PRODUCT_STOCK_INVALID.getCode());
		}

		verify(iProductRepositoryMock).findByCode(any(String.class));
		verify(iProductRepositoryMock, times(0)).save(any(Product.class));
	}

	@Test
	void saveProduct_PriceEqualsZero() {

		final ProductRequest productRequestSample = ProductSample.getProductDtoPriceZero();
		when(iProductRepositoryMock.findByCode(any(String.class))).thenReturn(Optional.empty());

		try {
			final ProductResponse productRes = productServiceImpl.saveProduct(productRequestSample);
			fail();
		} catch (BusinessAppException e) {
			assertEquals(e.getCode(), ExceptionCodes.PRODUCT_PRICE_INVALID.getCode());
		}

		verify(iProductRepositoryMock).findByCode(any(String.class));
		verify(iProductRepositoryMock, times(0)).save(any(Product.class));
	}

	@Test
	void getAllProductsTest() {

		when(iProductRepositoryMock.findAll()).thenReturn(ProductSample.getProductList());

		List<ProductResponse> productList = productServiceImpl.findProducts();

		verify(iProductRepositoryMock).findAll();

		assertEquals(2, productList.size());
	}

	@Test
	void getAllProducts_EmptyListTest() {

		when(iProductRepositoryMock.findAll()).thenReturn(Collections.emptyList());

		List<ProductResponse> productList = productServiceImpl.findProducts();

		verify(iProductRepositoryMock).findAll();

		assertTrue(productList.isEmpty());
	}

	@Test
	void getAllProductsByIdsTest() {

		when(iProductRepositoryMock.findAllById(any())).thenReturn(ProductSample.getProductList());

		List<Product> productList = productServiceImpl.findProductsByIds(ProductSample.getListIds());

		verify(iProductRepositoryMock).findAllById(any());

		assertEquals(2, productList.size());
	}

	@Test
	void getAllProductsByIds_EmptyListTest() {

		when(iProductRepositoryMock.findAllById(any())).thenReturn(Collections.emptyList());

		List<Product> productList = productServiceImpl.findProductsByIds(ProductSample.getListIds());

		verify(iProductRepositoryMock).findAllById(ProductSample.getListIds());

		assertTrue(productList.isEmpty());
	}

	@Test
	void updateProductSuccessful() {

		final ProductRequest productRequestSample = ProductSample.getProductDto();
		final Product product = ProductSample.getProduct();
		when(iProductRepositoryMock.findById(any(Integer.class))).thenReturn(Optional.of(product));
		when(iProductRepositoryMock.save(any(Product.class))).thenReturn(product);
		when(productMapper.toProduct(any(ProductRequest.class))).thenReturn(product);

		productServiceImpl.updateProduct(productRequestSample);

		verify(iProductRepositoryMock).findById(any(Integer.class));
		verify(iProductRepositoryMock).save(any(Product.class));
	}

	@Test
	void updateProduct_ProductNotExist() {

		when(iProductRepositoryMock.findById(any(Integer.class))).thenReturn(Optional.empty());

		try {
			final Product productRes = productServiceImpl.updateProduct(ProductSample.getProduct());
			fail();
		} catch (BusinessAppException e) {
			assertEquals(e.getCode(), ExceptionCodes.PRODUCT_ID_NOT_EXIST.getCode());
		}

		verify(iProductRepositoryMock).findById(any(Integer.class));
		verify(iProductRepositoryMock, times(0)).save(any(Product.class));
	}
}
