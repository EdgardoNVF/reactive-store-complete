package com.bancolombia.reactive_store;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bancolombia.reactive_store.model.ProductEntity;
import com.bancolombia.reactive_store.repository.IProductRepositorySQL;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class ReactiveStoreApplicationTests {

	@Mock
	IProductRepositorySQL repository;

	@Test
	void findAllTest(){
		/*Flujo emulado*/
		ProductEntity product=new ProductEntity();
		product.setName("Laptop");
		product.setCategory("electronicos");
		product.setId(2L);
		product.setPrice(new BigDecimal(150));

		when(repository.findAll()).thenReturn(Flux.just(product));

		StepVerifier.create(repository.findAll())
					.expectNext(product)
					.verifyComplete();

	}

}
