package tech.noetzold.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;
import tech.noetzold.model.PromotionModel;
import tech.noetzold.service.PromotionService;

import java.util.UUID;

@Path("/api/promo/v1/promotion")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PromotionController {
    
    @Inject
    PromotionService promotionService;

    @Channel("promotions")
    Emitter<PromotionModel> quoteRequestEmitter;

    private static final Logger logger = Logger.getLogger(PromotionController.class);

    @GET
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response getPromotionModelById(@PathParam("id") String id){
        UUID uuid = UUID.fromString(id);

        PromotionModel promotionModel = promotionService.findPromotionModelById(uuid);

        if(promotionModel.getPromoId() == null){
            logger.error("There is no promotion with id: " + id);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(promotionModel).build();
    }

    @POST
    @RolesAllowed("admin")
    public Response savePromotionModel(PromotionModel promotionModel){
        try {
            if (promotionModel.getPromoName() == null) {
                logger.error("Error to create Promotion without name: " + promotionModel.getPromoId());
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            promotionModel.setPromoId(null);
            quoteRequestEmitter.send(promotionModel);
            logger.info("Create " + promotionModel.getPromoId());
            return Response.status(Response.Status.CREATED).entity(promotionModel).build();
        } catch (Exception e) {
            logger.error("Error to create promotionModel: " + promotionModel.getPromoId());
            e.printStackTrace();
        }
        logger.error("Error to create promotionModel: " + promotionModel.getPromoId());
        return Response.status(Response.Status.BAD_REQUEST).entity(promotionModel).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response updatePromotionModel(@PathParam("id") String id, PromotionModel updatedPromotionModel) {
        if (id.isBlank() || updatedPromotionModel.getPromoId() == null) {
            logger.warn("Error to update promotionModel: " + id);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        PromotionModel existingPromotionModel = promotionService.findPromotionModelById(UUID.fromString(id));
        if (existingPromotionModel.getPromoId() == null) {
            logger.warn("Error to update promotionModel: " + id);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        promotionService.updatePromotionModel(updatedPromotionModel);

        return Response.ok(updatedPromotionModel).build();
    }

    @DELETE
    @RolesAllowed("admin")
    public Response deletePromotionModel(@PathParam("id") String id){
        if (id.isBlank()) {
            logger.warn("Error to delete promotionModel: " + id);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        UUID uuid = UUID.fromString(id);

        promotionService.deletePromotionModelById(uuid);

        return Response.ok().build();
    }

}
