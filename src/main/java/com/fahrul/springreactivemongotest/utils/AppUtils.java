package com.fahrul.springreactivemongotest.utils;

import org.springframework.beans.BeanUtils;

import com.fahrul.springreactivemongotest.dto.ProductDto;
import com.fahrul.springreactivemongotest.entity.Product;

public class AppUtils {

	public static ProductDto entityToDto(Product product) {
		ProductDto productDto = new ProductDto();
		BeanUtils.copyProperties(product, productDto);
		return productDto;
	}

	public static Product dtoToEntity(ProductDto productDto) {
		Product product = new Product();
		BeanUtils.copyProperties(productDto, product);
		return product;
	}

}
