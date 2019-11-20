package com.redhat.training.juanzu.helloworld;

import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import com.redhat.training.juanzu.helloworld.dao.SalutationLogDAO;
import com.redhat.training.juanzu.helloworld.dao.Salute;
import com.redhat.training.juanzu.helloworld.json.OfficerParser;
import com.redhat.training.juanzu.helloworld.salute.Officer;

@Path("/salute")
@Produces(MediaType.TEXT_PLAIN)
@ApplicationScoped
public class SaluteController {

    @Inject
    private Config config;

    @Inject
    @ConfigProperty(name = "officer", defaultValue = "in the room")
    private String officerProperty;

    @Inject
    @ConfigProperty(name = "officerObject", defaultValue = "{}")
    private Officer officerObject;

	@Inject
	private OfficerParser parser;

    @Inject
    @Named("SalutationLogDAO")
    private SalutationLogDAO salutationLogDAO;

    @GET
    public String attention () {
        return "Attention";
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String attentionOficcerByConfig (@FormParam("officer") String officer) {
        String message = attention() + ", officer "
                + Optional.ofNullable(officer)
                          .orElseGet( () -> config.getOptionalValue("officer", String.class).orElse("in the room"));
        salutationLogDAO.save(message);
        return message;
    }

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public Salute attentionOfficerFromParam (String officer) {
        String message = attention() + ", officer "
					+ Optional.ofNullable(officer).map(parser::parse).map(Officer::toString).orElse("in the room");
        return salutationLogDAO.save(message);
	}

    @POST
    @Path("byproperty")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String attentionOficcerByProperty () {
        String message = attention() + ", officer " + officerProperty;
        salutationLogDAO.save(message);
        return message;
    }

    @POST
    @Path("byobject")
    @Consumes(MediaType.APPLICATION_JSON)
    public String attentionOfficerByObject (Officer officer) {
        Officer inRoom = Optional.ofNullable(officer).filter(o -> null != o.getRange()).orElse(officerObject);
        String message = attention() + ", officer " + inRoom.getRange();
        salutationLogDAO.save(message);
        return message;
    }

    @DELETE
    @Path("withhold/{id}")
    public Response withhold (@PathParam("id") Integer id) {
        boolean result = salutationLogDAO.delete(Salute.builder().id(id).build());
        if (result) {
            return Response.noContent().build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

}
