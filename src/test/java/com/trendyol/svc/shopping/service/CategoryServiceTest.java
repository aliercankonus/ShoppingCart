package com.trendyol.svc.shopping.service;

import com.trendyol.svc.shopping.data.Category;
import com.trendyol.svc.shopping.exceptions.CategoryNotFoundException;
import com.trendyol.svc.shopping.repository.CategoryRepository;
import com.trendyol.svc.shopping.repository.entity.CategoryEntity;
import com.trendyol.svc.shopping.util.TestUtil;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.convert.ConversionService;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepositoryMock;
    @Mock
    private ConversionService conversionServiceMock;

    @InjectMocks private CategoryService underTest;

    @BeforeMethod
    public void setupMethod() {
        initMocks(this);
    }


    @Test
    public void shouldAddNewCategory(){
        Category category = TestUtil.createCategory("title");
        CategoryEntity categoryEntity = TestUtil.createCategoryEntity("title");

        when(conversionServiceMock.convert(category, CategoryEntity.class)).thenReturn(categoryEntity);
        when(categoryRepositoryMock.save(categoryEntity)).thenReturn(categoryEntity);
        when(conversionServiceMock.convert(categoryEntity, Category.class)).thenReturn(category);

        underTest.addCategory(category);

        verify(categoryRepositoryMock, times(1)).save(categoryEntity);
    }

    @Test(expectedExceptions = CategoryNotFoundException.class)
    public void shouldThrowExceptionWhenCategoryIsNull(){
        underTest.addCategory(null);
    }

}
