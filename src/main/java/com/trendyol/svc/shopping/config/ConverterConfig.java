package com.trendyol.svc.shopping.config;

import com.trendyol.svc.shopping.converter.CampaignEntityToCampaign;
import com.trendyol.svc.shopping.converter.CampaignToCampaignEntity;
import com.trendyol.svc.shopping.converter.CategoryEntityToCategory;
import com.trendyol.svc.shopping.converter.CategoryToCategoryEntity;
import com.trendyol.svc.shopping.converter.CouponEntityToCoupon;
import com.trendyol.svc.shopping.converter.CouponToCouponEntity;
import com.trendyol.svc.shopping.converter.ProductEntityToProduct;
import com.trendyol.svc.shopping.converter.ProductToProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.support.ConfigurableConversionService;

import javax.annotation.PostConstruct;

@Configuration
public class ConverterConfig {
    @Autowired
    @Qualifier("mvcConversionService")
    private ConfigurableConversionService conversionService;

    @PostConstruct
    public void postConstruct() {
        conversionService.addConverter(new CampaignEntityToCampaign());
        conversionService.addConverter(new CampaignToCampaignEntity());
        conversionService.addConverter(new CategoryEntityToCategory());
        conversionService.addConverter(new CategoryToCategoryEntity());
        conversionService.addConverter(new CouponEntityToCoupon());
        conversionService.addConverter(new CouponToCouponEntity());
        conversionService.addConverter(new ProductEntityToProduct());
        conversionService.addConverter(new ProductToProductEntity());
    }

    @Bean
    @Primary
    public ConfigurableConversionService getConversionService() {
        return conversionService;
    }
}
