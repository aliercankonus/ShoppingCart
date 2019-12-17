package com.trendyol.svc.shopping.service;

import com.trendyol.svc.shopping.data.Coupon;
import com.trendyol.svc.shopping.exceptions.CouponNotFoundException;
import com.trendyol.svc.shopping.repository.CouponRepository;
import com.trendyol.svc.shopping.repository.entity.CouponEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

import static com.trendyol.svc.shopping.util.Constants.COUPON_IS_SAVED;
import static com.trendyol.svc.shopping.util.Constants.COUPON_NOT_FOUND;

@Slf4j
@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final ConversionService conversionService;

    @Autowired
    public CouponService(CouponRepository couponRepository, ConversionService conversionService) {
        this.couponRepository = couponRepository;
        this.conversionService = conversionService;
    }

    public CouponEntity addCoupon(Coupon coupon) {
        if (coupon == null) {
            log.info(COUPON_NOT_FOUND);
            throw new CouponNotFoundException(COUPON_NOT_FOUND);
        }
        CouponEntity couponEntity = conversionService.convert(coupon, CouponEntity.class);
        CouponEntity savedCouponEntity = couponRepository.save(couponEntity);
        log.info(COUPON_IS_SAVED);
        return savedCouponEntity;
    }

    public List<Coupon> getAllCoupons() {
        return couponRepository
                .findAll()
                .stream()
                .map(couponEntity -> conversionService.convert(couponEntity, Coupon.class))
                .collect(Collectors.toList());
    }

    public BigDecimal calculateDiscount(double totalAmount, Coupon coupon) {
        BigDecimal discount = BigDecimal.ZERO;
        if (totalAmount > coupon.getMinimumAmount()) {
            discount =
                    coupon.getDiscountType()
                            .calculateDiscount(totalAmount, coupon.getDiscountValue())
                            .setScale(2, RoundingMode.FLOOR);
        }
        return discount;
    }
}
