package com.trendyol.svc.shopping.config;

import com.trendyol.svc.shopping.delivery.DeliveryCostCalculator;
import com.trendyol.svc.shopping.delivery.FixedDeliveryCostCalculator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${costPerDelivery}")
    private String costPerDelivery;

    @Value("${costPerProduct}")
    private String costPerProduct;

    @Value("${fixedCost}")
    private String fixedCost;

    @Bean
    public DeliveryCostCalculator deliveryCostCalculator() {

        return new FixedDeliveryCostCalculator(costPerDelivery, costPerProduct, fixedCost);
    }
}
