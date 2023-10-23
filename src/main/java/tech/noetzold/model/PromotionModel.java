package tech.noetzold.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;
import java.util.UUID;

public class PromotionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID promoId;
    private String promoName;
    private String promoDescription;
    private ValueType valueType;
    private Double value;
    private PromotionType promotionType;
    private String promoCompDesc;
    private Double promoCompType;
    private Double promoDtlId;
    private String rules;
    private Date startDate;
    private Date endDate;
    private String applyToCode;
    private Double discountLimit;
    private String exceptionParentId;

}
