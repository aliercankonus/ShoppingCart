package com.trendyol.svc.shopping.converter;

import com.trendyol.svc.shopping.data.Product;
import com.trendyol.svc.shopping.repository.entity.ProductEntity;
import org.springframework.core.convert.converter.Converter;

public class ProductEntityToProduct implements Converter<ProductEntity,Product> {
    @Override
    public Product convert(ProductEntity productEntity) {
        Product product = null;
        if(productEntity!=null){
            product = new Product();
            product.setCategory(new CategoryEntityToCategory().convert(productEntity.getCategoryEntity()));
            product.setPrice(productEntity.getPrice());
            product.setTitle(productEntity.getTitle());
        }
        return product;
    }
}
