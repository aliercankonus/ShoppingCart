package com.trendyol.svc.shopping.repository;

import com.trendyol.svc.shopping.repository.entity.CouponEntity;
import com.trendyol.svc.shopping.repository.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<CouponEntity, String> {

    CouponEntity findOneByCouponId(String id);
}
