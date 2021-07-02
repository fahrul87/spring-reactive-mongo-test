package com.fahrul.springreactivemongotest;

import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.fahrul.springreactivemongotest.controller.ProductController;
import com.fahrul.springreactivemongotest.dto.ProductDto;
import com.fahrul.springreactivemongotest.service.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@WebFluxTest(ProductController.class)
class SpringReactiveMongoTestApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private ProductService productService;

	@Test
	public void addProductTest() {
		Mono<ProductDto> productDtoToMono = Mono.just(new ProductDto("102", "Mobile", 1, 1000));
		when(productService.saveProduct(productDtoToMono)).thenReturn(productDtoToMono);

		webTestClient.post().uri("/products").body(Mono.just(productDtoToMono), ProductDto.class).exchange()
				.expectStatus().isOk();// 200
	}

	@Test
	public void getProductsTest() {
		Flux<ProductDto> productDtoToFlux = Flux.just(new ProductDto("102", "mobile", 1, 10000),
				new ProductDto("103", "TV", 1, 50000));
		when(productService.getProducts()).thenReturn(productDtoToFlux);

		Flux<ProductDto> respondeBody = webTestClient.get().uri("/products").exchange().expectStatus().isOk()
				.returnResult(ProductDto.class).getResponseBody();

		StepVerifier.create(respondeBody).expectSubscription().expectNext(new ProductDto("102", "mobile", 1, 10000))
				.expectNext(new ProductDto("103", "TV", 1, 50000)).verifyComplete();

	}

	@Test
	public void getProductTest() {
		Mono<ProductDto> productDtoMono = Mono.just(new ProductDto("102", "mobile", 1, 10000));
		when(productService.getProduct(any())).thenReturn(productDtoMono);

		Flux<ProductDto> responeBody = webTestClient.get().uri("/products/102").exchange().expectStatus().isOk()
				.returnResult(ProductDto.class).getResponseBody();

		StepVerifier.create(responeBody).expectSubscription().expectNextMatches(p -> p.getName().equals("mobile"))
				.verifyComplete();
	}

	@Test
	public void updateProductTest() {
		Mono<ProductDto> productDtoToMono = Mono.just(new ProductDto("102", "mobile", 1, 10000));
		when(productService.updateProduct(productDtoToMono, "102")).thenReturn(productDtoToMono);

		webTestClient.put().uri("/products/update/102").body(Mono.just(productDtoToMono), ProductDto.class).exchange()
				.expectStatus().isOk();// 200
	}

	@Test
	public void deleteProductTest() {
		given(productService.deleteProduct(any())).willReturn(Mono.empty());
		webTestClient.delete().uri("/products/delete/102").exchange().expectStatus().isOk();
	}

}
