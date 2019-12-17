package com.trendyol.svc.shopping.delivery;

import com.trendyol.svc.shopping.service.ShoppingCart;

public interface DeliveryCostCalculator {
    double calculateFor(ShoppingCart shoppingCart);
}
