package com.trendyol.svc.shopping.converter;

import com.trendyol.svc.shopping.data.Coupon;
import com.trendyol.svc.shopping.repository.entity.CouponEntity;
import org.springframework.core.convert.converter.Converter;

public class CouponEntityToCoupon implements Converter<CouponEntity,Coupon> {

    @Override
    public Coupon convert(CouponEntity couponEntity) {
        Coupon coupon = null;
        if(couponEntity!=null){
            coupon = new Coupon();
            coupon.setDiscountType(couponEntity.getDiscountType());
            coupon.setDiscountValue(couponEntity.getDiscountValue());
            coupon.setMinimumAmount(couponEntity.getMinimumAmount());
        }
        return coupon;
    }
}
