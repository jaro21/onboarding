package com.onboarding.payu.service.impl.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.onboarding.payu.model.ActiveType;
import com.onboarding.payu.model.product.request.ProductRequest;
import com.onboarding.payu.repository.entity.Product;
import org.springframework.stereotype.Component;

/**
 * Mapper for the Payment's objects
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class ProductMapper {

	public Product toProduct(final ProductRequest productRequest) {

		return Product.builder().idProduct(productRequest.getIdProduct())
					  .name(productRequest.getName())
					  .code(productRequest.getCode())
					  .description(productRequest.getDescription())
					  .price(productRequest.getPrice())
					  .stock(productRequest.getStock())
					  .active(productRequest.getActive() != null ? productRequest.getActive() : ActiveType.ACTIVE.getId())
					  .build();
	}

	public ProductRequest toProductDto(final Product product) {

		return ProductRequest.builder().idProduct(product.getIdProduct())
							 .name(product.getName())
							 .code(product.getCode())
							 .description(product.getDescription())
							 .price(product.getPrice())
							 .stock(product.getStock()).build();
	}

	public List<ProductRequest> toProductDtoList(final List<Product> productList) {

		return productList.stream().map(product -> toProductDto(product)).collect(Collectors.toList());
	}
}
