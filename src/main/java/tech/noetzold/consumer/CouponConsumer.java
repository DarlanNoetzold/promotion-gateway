package tech.noetzold.consumer;

import io.smallrye.reactive.messaging.annotations.Blocking;
import io.smallrye.reactive.messaging.annotations.Merge;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.noetzold.model.CouponModel;
import tech.noetzold.service.CouponService;

@ApplicationScoped
public class CouponConsumer {

    @Inject
    CouponService couponService;

    private static final Logger logger = LoggerFactory.getLogger(CouponService.class);

    @Incoming("coupons")
    @Merge
    @Blocking
    public CouponModel process(CouponModel incomingCouponModel) {

        couponService.saveCouponModel(incomingCouponModel);
        logger.info("Create Coupon " + incomingCouponModel.getCouponId() + ".");

        return incomingCouponModel;
    }
}
