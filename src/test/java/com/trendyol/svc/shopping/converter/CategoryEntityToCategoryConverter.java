package com.trendyol.svc.shopping.converter;

import com.trendyol.svc.shopping.data.Category;
import com.trendyol.svc.shopping.repository.entity.CategoryEntity;
import org.mockito.InjectMocks;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class CategoryEntityToCategoryConverter {

    @InjectMocks
    private CategoryEntityToCategory underTest;

    @BeforeMethod
    public void setupMethod() {
        initMocks(this);
    }

    @Test
    public void shouldConvertCategoryEntityToCategory(){
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setTitle("Title");
        categoryEntity.setParentCategoryId("ParentId");

        Category category = underTest.convert(categoryEntity);

        assertNotNull(category);
        assertEquals(category.getTitle(), categoryEntity.getTitle());
        assertEquals(category.getParentCategoryId(), categoryEntity.getParentCategoryId());
    }
}
