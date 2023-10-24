package tech.noetzold.service;

import io.quarkus.cache.CacheInvalidateAll;
import io.quarkus.cache.CacheResult;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import tech.noetzold.model.PromotionModel;
import tech.noetzold.repository.PromotionRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class PromotionService {

    @Inject
    PromotionRepository promotionRepository;

    @Transactional
    @CacheResult(cacheName = "promotion")
    public PromotionModel findPromotionModelById(UUID id){
        Optional<PromotionModel> optionalPromotionModel = promotionRepository.findByIdOptional(id);
        return optionalPromotionModel.orElse(new PromotionModel());
    }

    @Transactional
    @CacheInvalidateAll(cacheName = "promotion")
    public PromotionModel savePromotionModel(PromotionModel promotionModel){
        promotionRepository.persist(promotionModel);
        promotionRepository.flush();
        return promotionModel;
    }

    @Transactional
    @CacheInvalidateAll(cacheName = "promotion")
    public void updatePromotionModel(PromotionModel promotionModel){
        if (promotionModel == null || promotionModel.getPromoId() == null) {
            throw new WebApplicationException("Invalid data for promotionModel update", Response.Status.BAD_REQUEST);
        }

        PromotionModel existingPromotionModel = findPromotionModelById(promotionModel.getPromoId());
        if (existingPromotionModel == null) {
            throw new WebApplicationException("promotionModel not found", Response.Status.NOT_FOUND);
        }

        existingPromotionModel.setPromotionType(promotionModel.getPromotionType());
        existingPromotionModel.setApplyToCode(promotionModel.getApplyToCode());
        existingPromotionModel.setEndDate(promotionModel.getEndDate());
        existingPromotionModel.setRules(promotionModel.getRules());
        existingPromotionModel.setDiscountLimit(promotionModel.getDiscountLimit());
        existingPromotionModel.setExceptionParentId(promotionModel.getExceptionParentId());
        existingPromotionModel.setPromoCompDesc(promotionModel.getPromoCompDesc());
        existingPromotionModel.setPromoDescription(promotionModel.getPromoDescription());
        existingPromotionModel.setPromoDtlId(promotionModel.getPromoDtlId());
        existingPromotionModel.setStartDate(promotionModel.getStartDate());
        existingPromotionModel.setValueType(promotionModel.getValueType());
        existingPromotionModel.setValue(promotionModel.getValue());
        existingPromotionModel.setPromoName(promotionModel.getPromoName());
        existingPromotionModel.setPromoCompType(promotionModel.getPromoCompType());

        promotionRepository.persist(existingPromotionModel);
    }

    @Transactional
    @CacheInvalidateAll(cacheName = "promotion")
    public void deletePromotionModelById(UUID id) {
        promotionRepository.deleteById(id);
    }

    public List<PromotionModel> findAllPromotionModel() {
        PanacheQuery<PromotionModel> promotionModelPanacheQuery = promotionRepository.findAll();
        return promotionModelPanacheQuery.list();
    }
}
