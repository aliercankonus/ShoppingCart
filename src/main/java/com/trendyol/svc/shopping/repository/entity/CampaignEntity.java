package com.trendyol.svc.shopping.repository.entity;

import com.trendyol.svc.shopping.enums.DiscountType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
@Entity(name="campaign")
public class CampaignEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id")
    private String campaignId;

    @Column(name="discount")
    private double discountValue;

    @Column(name = "number_of_item")
    private int numberOfItem;

    @OneToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryEntity;

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;
}
