package com.trendyol.svc.shopping.converter;

import com.trendyol.svc.shopping.data.Coupon;
import com.trendyol.svc.shopping.enums.DiscountType;
import com.trendyol.svc.shopping.repository.entity.CouponEntity;
import org.mockito.InjectMocks;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class CouponEntityToCouponConverter {

    @InjectMocks
    private CouponEntityToCoupon underTest;

    @BeforeMethod
    public void setupMethod() {
        initMocks(this);
    }

    @Test
    public void shouldConvertCouponEntityToCoupon(){
        CouponEntity couponEntity = new CouponEntity();
        couponEntity.setMinimumAmount(100);
        couponEntity.setDiscountValue(10.0);
        couponEntity.setDiscountType(DiscountType.AMOUNT);

        Coupon coupon = underTest.convert(couponEntity);

        assertNotNull(coupon);
        assertEquals(coupon.getDiscountType(), couponEntity.getDiscountType());
        assertEquals(coupon.getDiscountValue(), couponEntity.getDiscountValue());
        assertEquals(coupon.getMinimumAmount(), couponEntity.getMinimumAmount());
    }
}
