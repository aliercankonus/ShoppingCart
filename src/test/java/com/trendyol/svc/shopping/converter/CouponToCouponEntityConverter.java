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

public class CouponToCouponEntityConverter {
    @InjectMocks
    private CouponToCouponEntity underTest;

    @BeforeMethod
    public void setupMethod() {
        initMocks(this);
    }

    @Test
    public void shouldConvertCouponToCouponEntity(){
        Coupon coupon = new Coupon();
        coupon.setMinimumAmount(100);
        coupon.setDiscountValue(10.0);
        coupon.setDiscountType(DiscountType.AMOUNT);

        CouponEntity couponEntity = underTest.convert(coupon);

        assertNotNull(coupon);
        assertEquals(coupon.getDiscountType(), couponEntity.getDiscountType());
        assertEquals(coupon.getDiscountValue(), couponEntity.getDiscountValue());
        assertEquals(coupon.getMinimumAmount(), couponEntity.getMinimumAmount());
    }
}
