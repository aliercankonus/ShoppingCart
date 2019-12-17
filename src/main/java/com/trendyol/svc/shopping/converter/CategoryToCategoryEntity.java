package com.trendyol.svc.shopping.converter;

import com.trendyol.svc.shopping.data.Category;
import com.trendyol.svc.shopping.repository.entity.CategoryEntity;
import org.springframework.core.convert.converter.Converter;

public class CategoryToCategoryEntity implements Converter<Category,CategoryEntity> {
    @Override
    public CategoryEntity convert(Category category) {
        CategoryEntity categoryEntity = null;
        if(category !=null){
            categoryEntity = new CategoryEntity();
            categoryEntity.setTitle(category.getTitle());
            categoryEntity.setParentCategoryId(category.getParentCategoryId());
        }

        return categoryEntity;
    }
}
