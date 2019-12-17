package com.trendyol.svc.shopping.converter;

import com.trendyol.svc.shopping.data.Category;
import com.trendyol.svc.shopping.data.Product;
import com.trendyol.svc.shopping.repository.entity.CategoryEntity;
import com.trendyol.svc.shopping.repository.entity.ProductEntity;
import org.mockito.InjectMocks;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class ProducToProductEntityConverter {

    @InjectMocks
    private ProductToProductEntity underTest;

    @BeforeMethod
    public void setupMethod() {
        initMocks(this);
    }


    @Test
    public void shouldConvertProductToProductEntity(){
        Product product = new Product();
        product.setTitle("Title");
        product.setPrice(6.99);

        Category category = new Category();
        category.setTitle("Title");
        category.setParentCategoryId("ParentId");
        product.setCategory(category);

        ProductEntity productEntity = underTest.convert(product);

        assertNotNull(product);
        assertEquals(product.getPrice(),productEntity.getPrice());
        assertEquals(product.getCategory().getTitle(),productEntity.getCategoryEntity().getTitle());
        assertEquals(product.getCategory().getParentCategoryId(),productEntity.getCategoryEntity().getParentCategoryId());
    }
}
