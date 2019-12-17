package com.trendyol.svc.shopping.controller;

import com.trendyol.svc.shopping.data.Campaign;
import com.trendyol.svc.shopping.data.Category;
import com.trendyol.svc.shopping.data.Coupon;
import com.trendyol.svc.shopping.data.Product;
import com.trendyol.svc.shopping.delivery.DeliveryCostCalculator;
import com.trendyol.svc.shopping.enums.DiscountType;
import com.trendyol.svc.shopping.service.CampaignService;
import com.trendyol.svc.shopping.service.CategoryService;
import com.trendyol.svc.shopping.service.CouponService;
import com.trendyol.svc.shopping.service.ProductService;
import com.trendyol.svc.shopping.service.ShoppingCart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import static com.trendyol.svc.shopping.util.Constants.ALMOND_PRODUCT;
import static com.trendyol.svc.shopping.util.Constants.APPLE_PRODUCT;
import static com.trendyol.svc.shopping.util.Constants.BABY_CATEGORY;
import static com.trendyol.svc.shopping.util.Constants.BABY_SHAMPOO_PRODUCT;
import static com.trendyol.svc.shopping.util.Constants.FOOD_CATEGORY;
import static com.trendyol.svc.shopping.util.Constants.FRUIT_CATEGORY;
import static com.trendyol.svc.shopping.util.Constants.ROLEX_PRODUCT;
import static com.trendyol.svc.shopping.util.Constants.WATCH_CATEGORY;

@Slf4j
@RestController
@RequestMapping(path = "api/v1")
public class ShoppingController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final CampaignService campaignService;
    private final CouponService couponService;
    private final DeliveryCostCalculator deliveryCostCalculator;
    private final ConversionService conversionService;

    public ShoppingController(
            ProductService productService,
            CategoryService categoryService,
            CampaignService campaignService,
            CouponService couponService,
            DeliveryCostCalculator deliveryCostCalculator,
            ConversionService conversionService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.campaignService = campaignService;
        this.couponService = couponService;
        this.deliveryCostCalculator = deliveryCostCalculator;
        this.conversionService = conversionService;
    }

    @GetMapping(path = "/shopping")
    public void startShopping() {
        createCategory();
        createProduct();
        createCampaign();
        createCoupon();

        ShoppingCart shoppingCart =
                new ShoppingCart(campaignService, couponService, deliveryCostCalculator);
        shoppingCart.addItem(getProduct(APPLE_PRODUCT), 5);
        shoppingCart.addItem(getProduct(ALMOND_PRODUCT), 3);
        shoppingCart.addItem(getProduct(ROLEX_PRODUCT), 1);
        shoppingCart.addItem(getProduct(BABY_SHAMPOO_PRODUCT), 7);

        shoppingCart.applyDiscounts(
                campaignService.getAllCampaigns().stream().toArray(Campaign[]::new));

        shoppingCart.applyCoupon(couponService.getAllCoupons().get(0));

        shoppingCart.print();
    }

    private void createCategory() {
        Category foodCategory = new Category(FOOD_CATEGORY);
        Category fruitCategory = new Category(FRUIT_CATEGORY);
        Category watchCategory = new Category(WATCH_CATEGORY);
        Category babyAndMotherCategory = new Category(BABY_CATEGORY);

        List<Category> categoryList =
                Arrays.asList(foodCategory, fruitCategory, watchCategory, babyAndMotherCategory);

        for (Category category : categoryList) {
            categoryService.addCategory(category);
        }
    }

    private void createProduct() {
        Product fruitProduct = new Product(APPLE_PRODUCT, 10.0, getCategory(FRUIT_CATEGORY));
        Product almondProduct = new Product(ALMOND_PRODUCT, 10.0, getCategory(FRUIT_CATEGORY));
        Product watchProduct = new Product(ROLEX_PRODUCT, 5000.0, getCategory(WATCH_CATEGORY));
        Product babyProduct = new Product(BABY_SHAMPOO_PRODUCT, 500.0, getCategory(BABY_CATEGORY));
        List<Product> productList =
                Arrays.asList(fruitProduct, almondProduct, watchProduct, babyProduct);

        for (Product product : productList) {
            productService.addProduct(product);
        }
    }

    private void createCampaign() {
        Campaign fruitCampaign =
                new Campaign(getCategory(FRUIT_CATEGORY), 10, 5, DiscountType.RATE);
        Campaign watchCategory =
                new Campaign(getCategory(WATCH_CATEGORY), 10, 2, DiscountType.RATE);
        Campaign babyCategory =
                new Campaign(getCategory(BABY_CATEGORY), 15, 6, DiscountType.AMOUNT);
        List<Campaign> campaignList = Arrays.asList(fruitCampaign, watchCategory, babyCategory);

        for (Campaign campaign : campaignList) {
            campaignService.addCampaign(campaign);
        }
    }

    private void createCoupon() {
        Coupon couponMinimum100 = new Coupon(100, 10.0, DiscountType.RATE);
        Coupon couponMinimum200 = new Coupon(200, 15.0, DiscountType.RATE);
        List<Coupon> couponList = Arrays.asList(couponMinimum100, couponMinimum200);

        for (Coupon coupon : couponList) {
            couponService.addCoupon(coupon);
        }
    }

    private Category getCategory(String title) {
        return conversionService.convert(categoryService.getCategory(title), Category.class);
    }

    private Product getProduct(String title) {
        return conversionService.convert(productService.getProduct(title), Product.class);
    }
}
