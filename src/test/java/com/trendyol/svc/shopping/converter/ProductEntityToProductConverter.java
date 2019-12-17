package com.trendyol.svc.shopping.converter;

import com.trendyol.svc.shopping.data.Product;
import com.trendyol.svc.shopping.repository.entity.CategoryEntity;
import com.trendyol.svc.shopping.repository.entity.ProductEntity;
import org.mockito.InjectMocks;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class ProductEntityToProductConverter {

    @InjectMocks
    private ProductEntityToProduct underTest;

    @BeforeMethod
    public void setupMethod() {
        initMocks(this);
    }


    @Test
    public void shouldConvertProductEntityToProduct(){
        ProductEntity productEntity = new ProductEntity();
        productEntity.setTitle("Title");
        productEntity.setPrice(6.99);

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setTitle("Title");
        categoryEntity.setParentCategoryId("ParentId");
        productEntity.setCategoryEntity(categoryEntity);

        Product product = underTest.convert(productEntity);

        assertNotNull(product);
        assertEquals(product.getPrice(),productEntity.getPrice());
        assertEquals(product.getCategory().getTitle(),productEntity.getCategoryEntity().getTitle());
        assertEquals(product.getCategory().getParentCategoryId(),productEntity.getCategoryEntity().getParentCategoryId());
    }
}
