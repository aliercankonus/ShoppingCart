package com.trendyol.svc.shopping.converter;

import com.trendyol.svc.shopping.data.Campaign;
import com.trendyol.svc.shopping.enums.DiscountType;
import com.trendyol.svc.shopping.repository.entity.CampaignEntity;
import com.trendyol.svc.shopping.repository.entity.CategoryEntity;
import org.mockito.InjectMocks;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import static org.mockito.MockitoAnnotations.initMocks;

public class CampaignEntityToCampaignConverter {

    @InjectMocks
    private CampaignEntityToCampaign underTest;

    @BeforeMethod
    public void setupMethod() {
        initMocks(this);
    }


    @Test
    public void shouldConvertCampaignEntityToCampaign(){
        CampaignEntity campaignEntity = new CampaignEntity();
        campaignEntity.setDiscountType(DiscountType.RATE);
        campaignEntity.setNumberOfItem(4);
        campaignEntity.setDiscountValue(10.0);

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setTitle("Title");
        categoryEntity.setParentCategoryId("ParentId");
        campaignEntity.setCategoryEntity(categoryEntity);

        Campaign campaign = underTest.convert(campaignEntity);

        assertNotNull(campaign);
        assertEquals(campaign.getDiscountType(),campaignEntity.getDiscountType());
        assertEquals(campaign.getDiscountValue(), campaignEntity.getDiscountValue());
        assertEquals(campaign.getNumberOfItem(), campaignEntity.getNumberOfItem());
        assertEquals(campaign.getCategory().getTitle(), campaignEntity.getCategoryEntity().getTitle());
        assertEquals(campaign.getCategory().getParentCategoryId(), campaignEntity.getCategoryEntity().getParentCategoryId());

    }
}
