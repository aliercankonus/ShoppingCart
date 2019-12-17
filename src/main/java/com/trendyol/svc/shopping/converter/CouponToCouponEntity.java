package com.trendyol.svc.shopping.converter;

import com.trendyol.svc.shopping.data.Coupon;
import com.trendyol.svc.shopping.repository.entity.CouponEntity;
import org.springframework.core.convert.converter.Converter;

public class CouponToCouponEntity implements Converter<Coupon,CouponEntity> {
    @Override
    public CouponEntity convert(Coupon coupon) {
        CouponEntity couponEntity=null;
        if(coupon!=null){
            couponEntity = new CouponEntity();
            couponEntity.setDiscountType(coupon.getDiscountType());
            couponEntity.setDiscountValue(coupon.getDiscountValue());
            couponEntity.setMinimumAmount(coupon.getMinimumAmount());
        }
        return couponEntity;
    }
}
