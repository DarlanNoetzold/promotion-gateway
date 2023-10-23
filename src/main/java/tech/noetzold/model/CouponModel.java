package tech.noetzold.model;

import jakarta.persistence.*;
import org.wildfly.common.annotation.NotNull;

import java.util.UUID;

public class CouponModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID couponId;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "promoId")
    private PromotionModel promotion;

    private String code;

}
