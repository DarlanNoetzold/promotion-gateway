package tech.noetzold.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import tech.noetzold.model.CouponModel;
import tech.noetzold.model.PromotionModel;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class CouponRepository implements PanacheRepository<CouponModel> {

    public Optional<CouponModel> findByIdOptional(UUID id) {
        return find("couponId", id).firstResultOptional();
    }

    public void deleteById(UUID id) {
        delete("couponId", id);
    }
}
