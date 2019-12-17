package com.trendyol.svc.shopping.service;

import com.trendyol.svc.shopping.data.Coupon;
import com.trendyol.svc.shopping.exceptions.CouponNotFoundException;
import com.trendyol.svc.shopping.repository.CouponRepository;
import com.trendyol.svc.shopping.repository.entity.CouponEntity;
import com.trendyol.svc.shopping.util.TestUtil;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.core.convert.ConversionService;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;

public class CouponServiceTest {

    @Mock private CouponRepository couponRepositoryMock;
    @Mock private ConversionService conversionServiceMock;

    @InjectMocks
    private CouponService underTest;

    @BeforeMethod
    public void setupMethod() {
        initMocks(this);
    }


    @Test
    public void shouldAddNewCoupon(){
        Coupon coupon = TestUtil.createCoupon();
        CouponEntity couponEntity = TestUtil.createCouponEntity();

        when(conversionServiceMock.convert(coupon, CouponEntity.class)).thenReturn(couponEntity);
        when(couponRepositoryMock.save(couponEntity)).thenReturn(couponEntity);

        underTest.addCoupon(coupon);

        Mockito.verify(couponRepositoryMock,times(1)).save(couponEntity);
    }

    @Test(expectedExceptions = CouponNotFoundException.class)
    public void shouldThrowExceptionWhenCouponIsnull(){
        underTest.addCoupon(null);
    }


    @Test
    public void shouldCalculateCouponDiscount(){
        Coupon coupon = TestUtil.createCoupon();
        BigDecimal couponDiscount = underTest.calculateDiscount(200, coupon);
        assertEquals(couponDiscount.doubleValue(), 10.0);

    }
}
