package com.trendyol.svc.shopping.delivery;

import com.trendyol.svc.shopping.service.ShoppingCart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class FixedDeliveryCostCalculator implements DeliveryCostCalculator {

    private String costPerDelivery;
    private String costPerProduct;
    private String fixedCost;

    public FixedDeliveryCostCalculator(
            @Value("${costPerDelivery}") String costPerDelivery,
            @Value("${costPerProduct}") String costPerProduct,
            @Value("${fixedCost}") String fixedCost) {
        this.costPerDelivery = costPerDelivery;
        this.costPerProduct = costPerProduct;
        this.fixedCost = fixedCost;

    }

    @Override
    public double calculateFor(ShoppingCart cart) {
        BigDecimal deliveryPrice =
                BigDecimal.valueOf(Double.parseDouble(costPerDelivery))
                        .multiply(BigDecimal.valueOf(cart.getNumberOfDeliveries()));
        BigDecimal productPrice =
                BigDecimal.valueOf(Double.parseDouble(costPerProduct))
                        .multiply(BigDecimal.valueOf(cart.getNumberOfProducts()));
        return deliveryPrice
                .add(productPrice)
                .add(BigDecimal.valueOf(Double.parseDouble(fixedCost)))
                .doubleValue();
    }
}
