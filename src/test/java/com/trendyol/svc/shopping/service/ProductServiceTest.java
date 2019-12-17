package com.trendyol.svc.shopping.service;

import com.trendyol.svc.shopping.data.Category;
import com.trendyol.svc.shopping.data.Product;
import com.trendyol.svc.shopping.exceptions.ProductNotFoundException;
import com.trendyol.svc.shopping.repository.ProductRepository;
import com.trendyol.svc.shopping.repository.entity.CategoryEntity;
import com.trendyol.svc.shopping.repository.entity.ProductEntity;
import com.trendyol.svc.shopping.util.TestUtil;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.convert.ConversionService;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ProductServiceTest {

    @Mock
    private CategoryService categoryServiceMock;
    @Mock private ProductRepository productRepositoryMock;
    @Mock private ConversionService conversionServiceMock;

    @InjectMocks
    private ProductService underTest;

    @BeforeMethod
    public void setupMethod() {
        initMocks(this);
    }


    @Test
    public void shouldAddNewCampagin(){

        Category category = TestUtil.createCategory("title");
        Product Product = TestUtil.createProduct("title",category);
        CategoryEntity categoryEntity = TestUtil.createCategoryEntity("title");
        ProductEntity productEntity = TestUtil.createProductEntity("title",categoryEntity);

        when(conversionServiceMock.convert(Product,ProductEntity.class)).thenReturn(productEntity);
        when(categoryServiceMock.getCategory(anyString())).thenReturn(categoryEntity);
        when(productRepositoryMock.save(productEntity)).thenReturn(productEntity);
        when(conversionServiceMock.convert(productEntity,Product.class)).thenReturn(Product);

        underTest.addProduct(Product);

        verify(productRepositoryMock, times(1)).save(productEntity);
    }

    @Test(expectedExceptions = ProductNotFoundException.class)
    public void shouldThrowExceptionWhenProductIsNull(){
        underTest.addProduct(null);
    }

}
