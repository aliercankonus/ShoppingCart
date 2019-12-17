package com.trendyol.svc.shopping.enums;

import java.math.BigDecimal;
import java.math.RoundingMode;

public enum DiscountType {
    AMOUNT {
        @Override
        public BigDecimal calculateDiscount(double totalAmount, double discountValue) {
                return BigDecimal.valueOf(discountValue);
        }
    },
    RATE {
        @Override
        public BigDecimal calculateDiscount(
                double totalAmount, double discountValue) {
                return (BigDecimal.valueOf(totalAmount).multiply(BigDecimal.valueOf(discountValue)))
                        .divide(BigDecimal.valueOf(100)).setScale(2, RoundingMode.FLOOR);
        }
    };

    public abstract BigDecimal calculateDiscount(
            double totalAmount, double discountValue);
}
