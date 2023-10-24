package tech.noetzold.service;

import io.quarkus.cache.CacheInvalidateAll;
import io.quarkus.cache.CacheResult;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import tech.noetzold.model.CouponModel;
import tech.noetzold.repository.CouponRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class CouponService {
    @Inject
    CouponRepository couponRepository;

    @Transactional
    public List<CouponModel> findAllCouponModel(){
        PanacheQuery<CouponModel> allCouponModel = couponRepository.findAll();
        return allCouponModel.list();
    }
    @Transactional
    @CacheResult(cacheName = "coupon")
    public CouponModel findCouponModelById(UUID id){
        Optional<CouponModel> optionalCouponModel = couponRepository.findByIdOptional(id);
        return optionalCouponModel.orElse(new CouponModel());
    }

    @Transactional
    @CacheInvalidateAll(cacheName = "coupon")
    public CouponModel saveCouponModel(CouponModel couponModel){
        couponRepository.persist(couponModel);
        couponRepository.flush();
        return couponModel;
    }

    @Transactional
    @CacheInvalidateAll(cacheName = "coupon")
    public void updateCouponModel(CouponModel couponModel){
        if (couponModel == null || couponModel.getCouponId() == null) {
            throw new WebApplicationException("Invalid data for couponModel update", Response.Status.BAD_REQUEST);
        }

        CouponModel existingCouponModel = findCouponModelById(couponModel.getCouponId());
        if (existingCouponModel == null) {
            throw new WebApplicationException("couponModel not found", Response.Status.NOT_FOUND);
        }

        existingCouponModel.setCode(couponModel.getCode());
        existingCouponModel.setPromotion(couponModel.getPromotion());

        couponRepository.persist(existingCouponModel);
    }

    @Transactional
    @CacheInvalidateAll(cacheName = "coupon")
    public void deleteCouponModelById(UUID id){
        couponRepository.deleteById(id);
    }
}
