package com.fahrul.springreactivemongotest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fahrul.springreactivemongotest.dto.ProductDto;
import com.fahrul.springreactivemongotest.service.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping
	public Flux<ProductDto> getProducts() {
		return productService.getProducts();
	}

	@GetMapping("/{id}")
	public Mono<ProductDto> getProduct(@PathVariable String id) {
		return productService.getProduct(id);
	}

	@GetMapping("/product-range")
	public Flux<ProductDto> getProductBetweenRange(@RequestParam("min") double min, @RequestParam("max") double max) {
		return productService.getProductInRange(min, max);
	}

	@PostMapping
	public Mono<ProductDto> saveProduct(@RequestBody Mono<ProductDto> productDtoMono) {
		System.out.println("Controller method called...");
		return productService.saveProduct(productDtoMono);
	}

	@PutMapping("/update/{id}")
	public Mono<ProductDto> updateProduct(@RequestBody Mono<ProductDto> productDtoMono, @PathVariable String id) {
		return productService.updateProduct(productDtoMono, id);
	}

	@DeleteMapping("/delete/{id}")
	public Mono<Void> deleteProduct(@PathVariable String id) {
		return productService.deleteProduct(id);
	}

}
