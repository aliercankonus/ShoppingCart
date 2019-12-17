package com.trendyol.svc.shopping.converter;

import com.trendyol.svc.shopping.data.Category;
import com.trendyol.svc.shopping.repository.entity.CategoryEntity;
import org.mockito.InjectMocks;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class CategoryToCategoryEntitiyConverter {

    @InjectMocks
    private CategoryToCategoryEntity underTest;

    @BeforeMethod
    public void setupMethod() {
        initMocks(this);
    }

    @Test
    public void shouldConvertCategoryToCategoryEntity(){
        Category category = new Category();
        category.setTitle("Title");
        category.setParentCategoryId("ParentId");

        CategoryEntity categoryEntity = underTest.convert(category);

        assertNotNull(category);
        assertEquals(category.getTitle(), categoryEntity.getTitle());
        assertEquals(category.getParentCategoryId(), categoryEntity.getParentCategoryId());
    }
}
