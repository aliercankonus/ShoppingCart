package com.trendyol.svc.shopping.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Product {
    private String title;
    private double price;
    private Category category;

    public Product(String title, double price, Category category) {
        this.title = title;
        this.price = price;
        this.category = category;
    }
}
