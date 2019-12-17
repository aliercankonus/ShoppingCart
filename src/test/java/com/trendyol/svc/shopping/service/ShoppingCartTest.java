package com.trendyol.svc.shopping.service;

import com.trendyol.svc.shopping.data.Campaign;
import com.trendyol.svc.shopping.data.Category;
import com.trendyol.svc.shopping.data.Coupon;
import com.trendyol.svc.shopping.data.Product;
import com.trendyol.svc.shopping.delivery.DeliveryCostCalculator;
import com.trendyol.svc.shopping.enums.DiscountType;
import com.trendyol.svc.shopping.repository.CampaignRepository;
import com.trendyol.svc.shopping.repository.CategoryRepository;
import com.trendyol.svc.shopping.repository.CouponRepository;
import com.trendyol.svc.shopping.repository.ProductRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class ShoppingCartTest {

    @Mock private CampaignService campaignServiceMock;
    @Mock private CouponService couponServiceMock;
    @Mock private DeliveryCostCalculator deliveryCostCalculatorMock;

    private ShoppingCart underTest;

    @BeforeMethod
    public void setupMethod() {
        initMocks(this);
        underTest =
                new ShoppingCart(
                        campaignServiceMock, couponServiceMock, deliveryCostCalculatorMock);
    }

    @Test
    public void shouldAddTwoProductWithOneCategoryToCart() {

        // GIVEN
        Category fruitCategory = new Category("Fruit");
        Product productApple = new Product("Apple", 10.0, fruitCategory);
        Product productAlmond = new Product("Almond", 10.0, fruitCategory);

        // WHEN
        underTest.addItem(productAlmond, 5);
        underTest.addItem(productApple, 3);

        // THEN
        assertEquals(underTest.getNumberOfProducts(),2);
        assertEquals(underTest.getNumberOfDeliveries(),1);
        assertEquals(underTest.getTotalAmount().intValue(),80);
    }

    @Test
    public void shouldAddThreeProductWithTwoCategoryToCart() {
        // GIVEN
        Category fruitCategory = new Category("Fruit");
        Product productApple = new Product("Apple", 10.0, fruitCategory);
        Product productAlmond = new Product("Almond", 10.0, fruitCategory);

        Category watchCategory = new Category("Watch");
        Product productRolex = new Product("Rolex", 1000.0, watchCategory);

        // WHEN
        underTest.addItem(productAlmond, 5);
        underTest.addItem(productApple, 3);
        underTest.addItem(productRolex, 1);

        // THEN
        assertEquals(underTest.getNumberOfProducts(),3);
        assertEquals(underTest.getNumberOfDeliveries(),2);
        assertEquals(underTest.getTotalAmount().intValue(),1080);
    }

    @Test
    public void shouldApplyCampaignDiscountWithFourProductAndTwoCategoryInCart() {
        // GIVEN
        Category fruitCategory = new Category("Fruit");
        Product productApple = new Product("Apple", 10.0, fruitCategory);
        Product productAlmond = new Product("Almond", 10.0, fruitCategory);

        Category watchCategory = new Category("Watch");
        Product productRolex = new Product("Rolex", 1000.0, watchCategory);
        Product productFossil = new Product("Fossil", 500.0, watchCategory);

        Campaign campaignForWatch = new Campaign(watchCategory, 10.0, 2, DiscountType.RATE);
        Campaign campaignForFruit = new Campaign(fruitCategory, 5.0, 8, DiscountType.RATE);

        // WHEN
        when(campaignServiceMock.calculateDiscount(anyDouble(), anyInt(), eq(campaignForWatch)))
                .thenReturn(BigDecimal.valueOf(1000));
        when(campaignServiceMock.calculateDiscount(anyDouble(), anyInt(), eq(campaignForFruit)))
                .thenReturn(BigDecimal.valueOf(100));

        underTest.addItem(productAlmond, 5);
        underTest.addItem(productApple, 3);
        underTest.addItem(productRolex, 1);
        underTest.addItem(productFossil, 3);

        underTest.applyDiscounts(campaignForWatch, campaignForFruit);
        underTest.print();

        // THEN
        assertEquals(underTest.getNumberOfProducts(),4);
        assertEquals(underTest.getNumberOfDeliveries(),2);
        assertEquals(underTest.getTotalAmount().intValue(),2580);
        assertEquals(underTest.getCampaignDiscount().intValue(),1000);
        assertEquals(underTest.getTotalAmountAfterDiscount().intValue(),1580);
    }

    @Test
    public void shouldApplyCouponDiscountWithFiveProductAndTwoCategoryInCart() {
        // GIVEN
        Category fruitCategory = new Category("Fruit");
        Product productApple = new Product("Apple", 10.0, fruitCategory);
        Product productAlmond = new Product("Almond", 10.0, fruitCategory);
        Product productBanana = new Product("Banana", 15.0, fruitCategory);

        Category watchCategory = new Category("Watch");
        Product productRolex = new Product("Rolex", 1000.0, watchCategory);
        Product productFossil = new Product("Fossil", 500.0, watchCategory);

        Coupon couponForMin1000 = new Coupon(1000, 450, DiscountType.AMOUNT);

        // WHEN
        when(couponServiceMock.calculateDiscount(anyDouble(), eq(couponForMin1000)))
                .thenReturn(BigDecimal.valueOf(450));

        underTest.addItem(productAlmond, 5);
        underTest.addItem(productApple, 3);
        underTest.addItem(productBanana, 8);
        underTest.addItem(productRolex, 1);
        underTest.addItem(productFossil, 3);

        underTest.applyCoupon(couponForMin1000);
        underTest.print();

        // THEN
        assertEquals(underTest.getNumberOfProducts(),5);
        assertEquals(underTest.getNumberOfDeliveries(),2);
        assertEquals(underTest.getTotalAmount().intValue(),2700);
        assertEquals(underTest.getCouponDiscount().intValue(),450);
        assertEquals(underTest.getTotalAmountAfterDiscount().intValue(),2250);

    }
}
