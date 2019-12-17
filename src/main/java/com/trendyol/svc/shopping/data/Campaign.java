package com.trendyol.svc.shopping.data;

import com.trendyol.svc.shopping.enums.DiscountType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Campaign {
    private Category category;
    private double discountValue;
    private int numberOfItem;
    private DiscountType discountType;

    public Campaign(Category category, double discountValue, int numberOfItem, DiscountType discountType) {
        this.category = category;
        this.discountValue = discountValue;
        this.numberOfItem = numberOfItem;
        this.discountType = discountType;
    }
}
