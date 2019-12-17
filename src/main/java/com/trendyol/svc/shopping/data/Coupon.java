package com.trendyol.svc.shopping.data;

import com.trendyol.svc.shopping.enums.DiscountType;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Coupon {
    private int minimumAmount;
    private double discountValue;
    private DiscountType discountType;

    public Coupon(int minimumAmount, double discountValue, DiscountType discountType) {
        this.minimumAmount = minimumAmount;
        this.discountValue = discountValue;
        this.discountType = discountType;
    }
}
