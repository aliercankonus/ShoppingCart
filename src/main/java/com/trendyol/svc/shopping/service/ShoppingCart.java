package com.trendyol.svc.shopping.service;

import com.trendyol.svc.shopping.data.Campaign;
import com.trendyol.svc.shopping.data.Category;
import com.trendyol.svc.shopping.data.Coupon;
import com.trendyol.svc.shopping.data.Product;
import com.trendyol.svc.shopping.delivery.DeliveryCostCalculator;
import com.trendyol.svc.shopping.exceptions.ProductIsEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ShoppingCart {

    private Map<Category, Map<Product, Integer>> shoppingMap;
    private BigDecimal totalAmountAfterDiscount;
    private BigDecimal campaignDiscount;
    private BigDecimal couponDiscount;
    private final CampaignService campaignService;
    private final CouponService couponService;
    private final DeliveryCostCalculator deliveryCostCalculator;

    @Autowired
    public ShoppingCart(
            CampaignService campaignService,
            CouponService couponService,
            DeliveryCostCalculator deliveryCostCalculator) {
        this.deliveryCostCalculator = deliveryCostCalculator;
        this.shoppingMap = new HashMap<>();
        this.totalAmountAfterDiscount = BigDecimal.ZERO;
        this.campaignDiscount = BigDecimal.ZERO;
        this.couponDiscount = BigDecimal.ZERO;
        this.campaignService = campaignService;
        this.couponService = couponService;
    }

    public void addItem(Product product, int quantity) {
        if (product == null) {
            throw new ProductIsEmptyException("Product is null!");
        }

        if (quantity <= 0) {
            throw new ProductIsEmptyException("Product quantity cannot be less than 0!");
        }

        this.shoppingMap.put(product.getCategory(), getProductMapFromShopping(product, quantity));
    }

    private Map<Product, Integer> getProductMapFromShopping(Product product, int quantity) {

        Map<Product, Integer> productMap =
                this.shoppingMap.getOrDefault(product.getCategory(), new HashMap<>());

        productMap.merge(product, quantity, Integer::sum);
        return productMap;
    }

    public void applyDiscounts(Campaign... campaigns) {
        this.campaignDiscount = calculateMaximumCampaignDiscount(Arrays.asList(campaigns));
        this.totalAmountAfterDiscount = getTotalAmount().subtract(this.campaignDiscount);
    }

    private BigDecimal calculateMaximumCampaignDiscount(List<Campaign> campaignList) {
        BigDecimal maximumDiscount = BigDecimal.ZERO;
        BigDecimal discount;

        for (Campaign campaign : campaignList) {
            Category category = campaign.getCategory();
            if (this.shoppingMap.containsKey(category)) {
                int totalQuantityForCategory = getTotalQuantityForCategory(category);
                discount =
                        campaignService.calculateDiscount(
                                getTotalAmountForCategory(category).doubleValue(),
                                totalQuantityForCategory,
                                campaign);
                if (maximumDiscount.compareTo(discount) < 0) {
                    maximumDiscount = discount;
                }
            }
        }

        return maximumDiscount;
    }

    public void applyCoupon(Coupon coupon) {
        this.couponDiscount =
                couponService.calculateDiscount(getTotalAmount().doubleValue(), coupon);
        this.totalAmountAfterDiscount =
                getTotalAmount().subtract(this.campaignDiscount).subtract(this.couponDiscount);
    }

    public BigDecimal getTotalAmount() {
        return this.shoppingMap
                .values()
                .stream()
                .map(Map::entrySet)
                .flatMap(Collection::stream)
                .map(entry -> entry.getKey().getPrice() * entry.getValue())
                .map(BigDecimal::new)
                .reduce(new BigDecimal(0), BigDecimal::add)
                .setScale(2, RoundingMode.FLOOR);
    }

    public BigDecimal getTotalAmountForCategory(Category category) {
        BigDecimal totalAmountForCategory = BigDecimal.ZERO;
        if (this.shoppingMap.containsKey(category)) {
            totalAmountForCategory =
                    this.shoppingMap
                            .get(category)
                            .entrySet()
                            .stream()
                            .map(entry -> entry.getKey().getPrice() * entry.getValue())
                            .map(BigDecimal::new)
                            .reduce(new BigDecimal(0), BigDecimal::add)
                            .setScale(2, RoundingMode.FLOOR);
        }

        return totalAmountForCategory;
    }

    private int getTotalQuantityForCategory(Category category) {
        return this.shoppingMap.get(category).values().stream().mapToInt(Integer::intValue).sum();
    }

    public BigDecimal getTotalAmountAfterDiscount() {
        return this.totalAmountAfterDiscount;
    }

    public BigDecimal getCouponDiscount() {
        return this.couponDiscount;
    }

    public BigDecimal getCampaignDiscount() {
        return this.campaignDiscount;
    }

    public int getNumberOfDeliveries() {
        return this.shoppingMap.size();
    }

    public int getNumberOfProducts() {
        return this.shoppingMap
                .values()
                .stream()
                .flatMap(p -> p.keySet().stream())
                .collect(Collectors.counting())
                .intValue();
    }

    public double getDeliveryCost(DeliveryCostCalculator deliveryCostCalculator) {
        return deliveryCostCalculator.calculateFor(this);
    }

    public void print() {
        StringBuilder stringBuilder = new StringBuilder();
        Set<Map.Entry<Category, Map<Product, Integer>>> entries = this.shoppingMap.entrySet();

        for (Map.Entry<Category, Map<Product, Integer>> categoryEntry : entries) {
            stringBuilder
                    .append("\n")
                    .append("Category : ")
                    .append(categoryEntry.getKey().getTitle());
            stringBuilder.append("\n");
            Set<Map.Entry<Product, Integer>> productEntries = categoryEntry.getValue().entrySet();
            for (Map.Entry<Product, Integer> productEntry : productEntries) {
                stringBuilder
                        .append("Product : ")
                        .append(productEntry.getKey().getTitle())
                        .append(", Product price : ")
                        .append(productEntry.getKey().getPrice())
                        .append(", Quantity : ")
                        .append(productEntry.getValue())
                        .append("\n");
            }
        }
        stringBuilder.append("\n");
        stringBuilder
                .append("Campaign discount : ")
                .append(getCampaignDiscount())
                .append("\n")
                .append("Coupon discount : ")
                .append(getCouponDiscount())
                .append("\n")
                .append("Total amount : ")
                .append(getTotalAmount())
                .append("\n")
                .append("Total amount after discount : ")
                .append(getTotalAmountAfterDiscount())
                .append("\n")
                .append("Delivery cost : ")
                .append(getDeliveryCost(deliveryCostCalculator));

        System.out.println(stringBuilder);
    }
}
