package com.trendyol.svc.shopping.service;

import com.trendyol.svc.shopping.data.Product;
import com.trendyol.svc.shopping.exceptions.ProductNotFoundException;
import com.trendyol.svc.shopping.repository.ProductRepository;
import com.trendyol.svc.shopping.repository.entity.CategoryEntity;
import com.trendyol.svc.shopping.repository.entity.ProductEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import static com.trendyol.svc.shopping.util.Constants.PRODUCT_IS_SAVED;
import static com.trendyol.svc.shopping.util.Constants.PRODUCT_NOT_FOUND;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ConversionService conversionService;

    @Autowired
    public ProductService(
            ProductRepository productRepository,
            CategoryService categoryService,
            ConversionService conversionService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.conversionService = conversionService;
    }

    public Product addProduct(Product product) {
        if (product == null) {
            log.info(PRODUCT_NOT_FOUND);
            throw new ProductNotFoundException(PRODUCT_NOT_FOUND);
        }
        ProductEntity productEntity = conversionService.convert(product, ProductEntity.class);
        CategoryEntity categoryEntity =
                categoryService.getCategory(product.getCategory().getTitle());
        productEntity.setCategoryEntity(categoryEntity);
        ProductEntity savedProductEntity = productRepository.save(productEntity);
        log.info(PRODUCT_IS_SAVED);
        return conversionService.convert(savedProductEntity, Product.class);
    }

    public ProductEntity getProduct(String title) {
        ProductEntity productEntity = productRepository.findOneByTitle(title);
        if (productEntity == null) {
            log.info(PRODUCT_NOT_FOUND);
            throw new ProductNotFoundException(PRODUCT_NOT_FOUND);
        }
        return productEntity;
    }
}
