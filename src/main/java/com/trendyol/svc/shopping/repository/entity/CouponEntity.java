package com.trendyol.svc.shopping.repository.entity;

import com.trendyol.svc.shopping.enums.DiscountType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity(name="coupon")
public class CouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id")
    private String couponId;

    @Column(name = "minimum_amount")
    private int minimumAmount;

    @Column(name="discount_value")
    private double discountValue;

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;
}
