package com.trendyol.svc.shopping.service;

import com.trendyol.svc.shopping.data.Campaign;
import com.trendyol.svc.shopping.data.Category;
import com.trendyol.svc.shopping.exceptions.CampaignNotFoundException;
import com.trendyol.svc.shopping.repository.CampaignRepository;
import com.trendyol.svc.shopping.repository.entity.CampaignEntity;
import com.trendyol.svc.shopping.repository.entity.CategoryEntity;
import com.trendyol.svc.shopping.util.TestUtil;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.convert.ConversionService;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;

public class CampaignServiceTest {

    @Mock private CategoryService categoryServiceMock;
    @Mock private CampaignRepository campaignRepositoryMock;
    @Mock private ConversionService conversionServiceMock;

    @InjectMocks private CampaignService underTest;

    @BeforeMethod
    public void setupMethod() {
        initMocks(this);
    }


    @Test
    public void shouldAddNewCampagin(){

        Category category = TestUtil.createCategory("title");
        Campaign campaign = TestUtil.createCampaign(category);
        CategoryEntity categoryEntity = TestUtil.createCategoryEntity("title");
        CampaignEntity campaignEntity = TestUtil.createCampaignEntity(categoryEntity);

        when(conversionServiceMock.convert(campaign,CampaignEntity.class)).thenReturn(campaignEntity);
        when(categoryServiceMock.getCategory(anyString())).thenReturn(categoryEntity);
        when(campaignRepositoryMock.save(campaignEntity)).thenReturn(campaignEntity);
        when(conversionServiceMock.convert(campaignEntity,Campaign.class)).thenReturn(campaign);

        underTest.addCampaign(campaign);

        verify(campaignRepositoryMock, times(1)).save(campaignEntity);
    }

    @Test(expectedExceptions = CampaignNotFoundException.class)
    public void shouldThrowExceptionWhenCampaignIsNull(){
        underTest.addCampaign(null);
    }

    @Test
    public void shouldCalculateCampaignDiscount(){
        Category category = TestUtil.createCategory("title");
        Campaign campaign = TestUtil.createCampaign(category);
        BigDecimal campaignDiscount = underTest.calculateDiscount(1000,100,campaign);
        assertEquals(campaignDiscount.doubleValue(), 100.0);
    }

}

