package com.trendyol.svc.shopping.converter;

import com.trendyol.svc.shopping.data.Campaign;
import com.trendyol.svc.shopping.repository.entity.CampaignEntity;
import org.springframework.core.convert.converter.Converter;

public class CampaignEntityToCampaign implements Converter<CampaignEntity,Campaign> {
    @Override
    public Campaign convert(CampaignEntity campaignEntity) {
        Campaign campaign = null;
        if(campaignEntity!=null){
            campaign = new Campaign();
            campaign.setCategory(new CategoryEntityToCategory().convert(campaignEntity.getCategoryEntity()));
            campaign.setDiscountType(campaignEntity.getDiscountType());
            campaign.setDiscountValue(campaignEntity.getDiscountValue());
            campaign.setNumberOfItem(campaignEntity.getNumberOfItem());
        }
        return campaign;
    }
}
