package com.trendyol.svc.shopping.converter;

import com.trendyol.svc.shopping.data.Product;
import com.trendyol.svc.shopping.repository.entity.ProductEntity;
import org.springframework.core.convert.converter.Converter;

public class ProductToProductEntity implements Converter<Product,ProductEntity> {
    @Override
    public ProductEntity convert(Product product) {
        ProductEntity productEntity = null;
        if(product !=null){
            productEntity = new ProductEntity();
            productEntity.setPrice(product.getPrice());
            productEntity.setTitle(product.getTitle());
            productEntity.setCategoryEntity(new CategoryToCategoryEntity().convert(product.getCategory()));
        }
        
        return productEntity;
    }
}
