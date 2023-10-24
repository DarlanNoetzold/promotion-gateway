package tech.noetzold.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;
import tech.noetzold.model.CouponModel;
import tech.noetzold.service.CouponService;

import java.util.UUID;

@Path("/api/promo/v1/coupon")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CouponController {

    @Inject
    CouponService couponService;

    @Channel("coupons")
    Emitter<CouponModel> quoteRequestEmitter;

    private static final Logger logger = Logger.getLogger(CouponController.class);

    @GET
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response getCouponModelById(@PathParam("id") String id){
        UUID uuid = UUID.fromString(id);

        CouponModel couponModel = couponService.findCouponModelById(uuid);

        if(couponModel.getCouponId() == null){
            logger.error("There is no coupon with id: " + id);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(couponModel).build();
    }

    @POST
    @RolesAllowed("admin")
    public Response saveCouponModel(CouponModel couponModel){
        try {
            if (couponModel.getPromotion() == null) {
                logger.error("Error to create Coupon without promotion: " + couponModel.getCouponId());
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            couponModel.setCouponId(null);
            quoteRequestEmitter.send(couponModel);
            logger.info("Create " + couponModel.getCouponId());
            return Response.status(Response.Status.CREATED).entity(couponModel).build();
        } catch (Exception e) {
            logger.error("Error to create couponModel: " + couponModel.getCouponId());
            e.printStackTrace();
        }
        logger.error("Error to create couponModel: " + couponModel.getCouponId());
        return Response.status(Response.Status.BAD_REQUEST).entity(couponModel).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response updateCouponModel(@PathParam("id") String id, CouponModel updatedCouponModel) {
        if (id.isBlank() || updatedCouponModel.getCouponId() == null) {
            logger.warn("Error to update couponModel: " + id);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        CouponModel existingCouponModel = couponService.findCouponModelById(UUID.fromString(id));
        if (existingCouponModel.getCouponId() == null) {
            logger.warn("Error to update couponModel: " + id);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        couponService.updateCouponModel(updatedCouponModel);

        return Response.ok(updatedCouponModel).build();
    }

    @DELETE
    @RolesAllowed("admin")
    public Response deleteCouponModel(@PathParam("id") String id){
        if (id.isBlank()) {
            logger.warn("Error to delete couponModel: " + id);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        UUID uuid = UUID.fromString(id);

        couponService.deleteCouponModelById(uuid);

        return Response.ok().build();
    }
}
