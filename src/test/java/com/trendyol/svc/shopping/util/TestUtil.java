package com.trendyol.svc.shopping.util;

import com.trendyol.svc.shopping.data.Campaign;
import com.trendyol.svc.shopping.data.Category;
import com.trendyol.svc.shopping.data.Coupon;
import com.trendyol.svc.shopping.data.Product;
import com.trendyol.svc.shopping.enums.DiscountType;
import com.trendyol.svc.shopping.repository.entity.CampaignEntity;
import com.trendyol.svc.shopping.repository.entity.CategoryEntity;
import com.trendyol.svc.shopping.repository.entity.CouponEntity;
import com.trendyol.svc.shopping.repository.entity.ProductEntity;

public class TestUtil {


    public static Campaign createCampaign(Category category){
        Campaign campaign = new Campaign();
        campaign.setDiscountType(DiscountType.RATE);
        campaign.setNumberOfItem(4);
        campaign.setDiscountValue(10.0);
        campaign.setCategory(category);

        return campaign;
    }

    public static CampaignEntity createCampaignEntity(CategoryEntity categoryEntity){
        CampaignEntity campaignEntity = new CampaignEntity();
        campaignEntity.setDiscountType(DiscountType.RATE);
        campaignEntity.setNumberOfItem(4);
        campaignEntity.setDiscountValue(10.0);
        campaignEntity.setCategoryEntity(categoryEntity);

        return campaignEntity;
    }

    public static Category createCategory(String title){
        Category category = new Category();
        category.setTitle(title);
        category.setParentCategoryId("ParentId");

        return category;
    }

    public static CategoryEntity createCategoryEntity(String title){
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setTitle(title);
        categoryEntity.setParentCategoryId("ParentId");

        return categoryEntity;
    }

    public static Coupon createCoupon(){
        Coupon coupon = new Coupon();
        coupon.setMinimumAmount(100);
        coupon.setDiscountValue(10.0);
        coupon.setDiscountType(DiscountType.AMOUNT);
        return  coupon;
    }

    public static CouponEntity createCouponEntity(){
        CouponEntity couponEntity = new CouponEntity();
        couponEntity.setMinimumAmount(100);
        couponEntity.setDiscountValue(10.0);
        couponEntity.setDiscountType(DiscountType.AMOUNT);
        return couponEntity;
    }

    public static Product createProduct(String title, Category category){
        Product product = new Product();
        product.setTitle(title);
        product.setPrice(6.99);
        product.setCategory(category);

        return product;
    }

    public static ProductEntity createProductEntity(String title, CategoryEntity categoryEntity){
        ProductEntity productEntity = new ProductEntity();
        productEntity.setTitle(title);
        productEntity.setPrice(6.99);
        productEntity.setCategoryEntity(categoryEntity);

        return productEntity;
    }
}
