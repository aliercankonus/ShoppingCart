package com.trendyol.svc.shopping.service;

import com.trendyol.svc.shopping.data.Campaign;
import com.trendyol.svc.shopping.exceptions.CampaignNotFoundException;
import com.trendyol.svc.shopping.repository.CampaignRepository;
import com.trendyol.svc.shopping.repository.entity.CampaignEntity;
import com.trendyol.svc.shopping.repository.entity.CategoryEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

import static com.trendyol.svc.shopping.util.Constants.CAMPAIGN_IS_SAVED;
import static com.trendyol.svc.shopping.util.Constants.CAMPAIGN_NOT_FOUND;

@Slf4j
@Service
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final CategoryService categoryService;
    private final ConversionService conversionService;

    @Autowired
    public CampaignService(
            CampaignRepository campaignRepository,
            CategoryService categoryService,
            ConversionService conversionService) {
        this.campaignRepository = campaignRepository;
        this.categoryService = categoryService;
        this.conversionService = conversionService;
    }

    public Campaign addCampaign(Campaign campaign) {
        if (campaign == null) {
            log.info(CAMPAIGN_NOT_FOUND);
            throw new CampaignNotFoundException(CAMPAIGN_NOT_FOUND);
        }
        CampaignEntity campaignEntity = conversionService.convert(campaign, CampaignEntity.class);
        CategoryEntity categoryEntity =
                categoryService.getCategory(campaign.getCategory().getTitle());
        campaignEntity.setCategoryEntity(categoryEntity);

        CampaignEntity savedCampaignEntity = campaignRepository.save(campaignEntity);
        log.info(CAMPAIGN_IS_SAVED);
        return conversionService.convert(savedCampaignEntity, Campaign.class);
    }

    public List<Campaign> getAllCampaigns() {
        List<CampaignEntity> campaignEntityList = campaignRepository.findAll();
        return campaignEntityList
                .stream()
                .map(entity -> conversionService.convert(entity, Campaign.class))
                .collect(Collectors.toList());
    }

    public BigDecimal calculateDiscount(
            double totalAmountForCategory, int totalQuantity, Campaign campaign) {
        BigDecimal discount = BigDecimal.ZERO;
        if (totalQuantity > campaign.getNumberOfItem()) {
            discount =
                    campaign.getDiscountType()
                            .calculateDiscount(totalAmountForCategory, campaign.getDiscountValue())
                            .setScale(2, RoundingMode.FLOOR);
        }
        return discount;
    }
}
