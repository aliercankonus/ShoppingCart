package com.trendyol.svc.shopping.converter;

import com.trendyol.svc.shopping.data.Category;
import com.trendyol.svc.shopping.repository.entity.CategoryEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class CategoryEntityToCategory implements Converter<CategoryEntity,Category> {

    @Override
    public Category convert(CategoryEntity categoryEntity) {
        Category category = null;
        if(categoryEntity!=null){
            category = new Category();
            category.setTitle(categoryEntity.getTitle());
            if(!StringUtils.isEmpty(categoryEntity.getParentCategoryId())){
                category.setParentCategoryId(categoryEntity.getParentCategoryId());
            }
        }
        return category;
    }
}
