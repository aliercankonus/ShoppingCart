package com.trendyol.svc.shopping.service;

import com.trendyol.svc.shopping.data.Category;
import com.trendyol.svc.shopping.exceptions.CategoryNotFoundException;
import com.trendyol.svc.shopping.repository.CategoryRepository;
import com.trendyol.svc.shopping.repository.entity.CategoryEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import static com.trendyol.svc.shopping.util.Constants.CATEGORY_IS_SAVED;
import static com.trendyol.svc.shopping.util.Constants.CATEGORY_NOT_FOUND;

@Slf4j
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ConversionService conversionService;

    @Autowired
    public CategoryService(
            CategoryRepository categoryRepository, ConversionService conversionService) {
        this.categoryRepository = categoryRepository;
        this.conversionService = conversionService;
    }

    public Category addCategory(Category category) {
        if (category == null) {
            log.info(CATEGORY_NOT_FOUND);
            throw new CategoryNotFoundException(CATEGORY_NOT_FOUND);
        }
        CategoryEntity categoryEntity = conversionService.convert(category, CategoryEntity.class);
        CategoryEntity savedCategoryEntity = categoryRepository.save(categoryEntity);
        log.info(CATEGORY_IS_SAVED);
        return conversionService.convert(savedCategoryEntity, Category.class);
    }

    public CategoryEntity getCategory(String title) {
        CategoryEntity categoryEntity = categoryRepository.findOneByTitle(title);
        if (categoryEntity == null) {
            log.info(CATEGORY_NOT_FOUND);
            throw new CategoryNotFoundException(CATEGORY_NOT_FOUND);
        }
        return categoryEntity;
    }
}
