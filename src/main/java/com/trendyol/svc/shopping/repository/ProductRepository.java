package com.trendyol.svc.shopping.repository;


import com.trendyol.svc.shopping.data.Category;
import com.trendyol.svc.shopping.repository.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, String> {


    ProductEntity findOneByProductId(String productId);

    ProductEntity findOneByTitle(String title);
}
