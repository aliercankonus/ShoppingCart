package com.trendyol.svc.shopping.repository;

import com.trendyol.svc.shopping.repository.entity.CategoryEntity;
import com.trendyol.svc.shopping.repository.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {


    CategoryEntity findOneByCategoryId(String categoryId);

    CategoryEntity findOneByTitle(String title);
}
