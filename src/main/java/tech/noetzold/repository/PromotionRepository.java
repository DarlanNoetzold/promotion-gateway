package tech.noetzold.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import tech.noetzold.model.PromotionModel;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class PromotionRepository implements PanacheRepository<PromotionModel> {

    public Optional<PromotionModel> findByIdOptional(UUID id) {
        return find("promoId", id).firstResultOptional();
    }

    public void deleteById(UUID id) {
        delete("promoId", id);
    }
}
