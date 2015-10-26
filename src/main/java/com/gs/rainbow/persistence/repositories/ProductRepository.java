package com.gs.rainbow.persistence.repositories;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.gs.rainbow.domain.Product;

@RepositoryRestResource(collectionResourceRel="product", path="product")
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
	List<Product> findByPrice(@Param("price") int price);
}


