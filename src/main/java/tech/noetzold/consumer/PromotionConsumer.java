package tech.noetzold.consumer;

import io.smallrye.reactive.messaging.annotations.Blocking;
import io.smallrye.reactive.messaging.annotations.Merge;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.noetzold.model.PromotionModel;
import tech.noetzold.service.PromotionService;

@ApplicationScoped
public class PromotionConsumer {

    @Inject
    PromotionService promotionService;

    private static final Logger logger = LoggerFactory.getLogger(PromotionService.class);

    @Incoming("promotions")
    @Merge
    @Blocking
    public PromotionModel process(PromotionModel incomingPromotionModel) {

        promotionService.savePromotionModel(incomingPromotionModel);
        logger.info("Create Promotion " + incomingPromotionModel.getPromoId() + ".");

        return incomingPromotionModel;
    }
}
