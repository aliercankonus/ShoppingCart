package com.trendyol.svc.shopping.converter;

import com.trendyol.svc.shopping.data.Campaign;
import com.trendyol.svc.shopping.data.Category;
import com.trendyol.svc.shopping.enums.DiscountType;
import com.trendyol.svc.shopping.repository.entity.CampaignEntity;
import com.trendyol.svc.shopping.repository.entity.CategoryEntity;
import org.mockito.InjectMocks;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class CampaignToCampaingEntityConverter {

    @InjectMocks
    private CampaignToCampaignEntity underTest;

    @BeforeMethod
    public void setupMethod() {
        initMocks(this);
    }

    @Test
    public void shouldConvertCampaignToCampaignEntity(){
        Campaign campaign = new Campaign();
        campaign.setDiscountType(DiscountType.RATE);
        campaign.setNumberOfItem(4);
        campaign.setDiscountValue(10.0);

        Category category = new Category();
        category.setTitle("Title");
        category.setParentCategoryId("ParentId");
        campaign.setCategory(category);

        CampaignEntity campaignEntity = underTest.convert(campaign);

        assertNotNull(campaignEntity);
        assertEquals(campaign.getDiscountType(),campaignEntity.getDiscountType());
        assertEquals(campaign.getDiscountValue(), campaignEntity.getDiscountValue());
        assertEquals(campaign.getNumberOfItem(), campaignEntity.getNumberOfItem());
        assertEquals(campaign.getCategory().getTitle(), campaignEntity.getCategoryEntity().getTitle());
        assertEquals(campaign.getCategory().getParentCategoryId(), campaignEntity.getCategoryEntity().getParentCategoryId());
    }
}
