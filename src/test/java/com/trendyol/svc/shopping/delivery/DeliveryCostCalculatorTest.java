package com.trendyol.svc.shopping.delivery;

import com.trendyol.svc.shopping.data.Category;
import com.trendyol.svc.shopping.data.Product;
import com.trendyol.svc.shopping.service.CampaignService;
import com.trendyol.svc.shopping.service.CouponService;
import com.trendyol.svc.shopping.service.ShoppingCart;
import org.mockito.Mock;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class DeliveryCostCalculatorTest {

    @Mock private CampaignService campaignServiceMock;
    @Mock private CouponService couponServiceMock;

    private DeliveryCostCalculator deliveryCostCalculator;

    @Test
    public void shouldCalculateFixedDeliveryCost(){
        deliveryCostCalculator = new FixedDeliveryCostCalculator("10","15","4.99");
        ShoppingCart shoppingCart = createShoppingCart(campaignServiceMock, couponServiceMock, deliveryCostCalculator);

        assertEquals(deliveryCostCalculator.calculateFor(shoppingCart), 69.99);
    }


    public ShoppingCart createShoppingCart(CampaignService campaignService, CouponService couponService, DeliveryCostCalculator deliveryCostCalculator){

        Category fruitCategory = new Category("Fruit");
        Product productApple = new Product("Apple", 10.0, fruitCategory);
        Product productAlmond = new Product("Almond", 10.0, fruitCategory);

        Category watchCategory = new Category("Watch");
        Product productRolex = new Product("Rolex", 1000.0, watchCategory);

        ShoppingCart shoppingCart = new ShoppingCart(campaignService, couponService, deliveryCostCalculator);
        shoppingCart.addItem(productAlmond, 5);
        shoppingCart.addItem(productApple, 3);
        shoppingCart.addItem(productRolex, 1);

        return shoppingCart;
    }
}
