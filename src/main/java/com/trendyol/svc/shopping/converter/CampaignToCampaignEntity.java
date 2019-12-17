package com.trendyol.svc.shopping.converter;

import com.trendyol.svc.shopping.data.Campaign;
import com.trendyol.svc.shopping.repository.entity.CampaignEntity;
import org.springframework.core.convert.converter.Converter;

public class CampaignToCampaignEntity implements Converter<Campaign, CampaignEntity> {
    @Override
    public CampaignEntity convert(Campaign campaign) {
        CampaignEntity campaignEntity = null;
        if (campaign != null) {
            campaignEntity = new CampaignEntity();
            campaignEntity.setCategoryEntity(
                    new CategoryToCategoryEntity().convert(campaign.getCategory()));
            campaignEntity.setDiscountValue(campaign.getDiscountValue());
            campaignEntity.setNumberOfItem(campaign.getNumberOfItem());
            campaignEntity.setDiscountType(campaign.getDiscountType());
        }
        return campaignEntity;
    }
}
