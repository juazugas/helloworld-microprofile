package com.redhat.training.juanzu.helloworld;

import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;
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

    @GET
    public String attention () {
        return "Attention";
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String attentionOficcerByConfig (@FormParam("officer") String officer) {
        return attention() + ", officer "
                + Optional.ofNullable(officer)
                          .orElseGet( () -> config.getOptionalValue("officer", String.class).orElse("in the room"));
    }

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String attentionOfficerFromParam (String officer) {
		return attention() + ", officer "
					+ Optional.ofNullable(officer).map(parser::parse).map(Officer::toString).orElse("in the room");
	}

    @POST
    @Path("byproperty")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String attentionOficcerByProperty () {
        return attention() + ", officer " + officerProperty;
    }

    @POST
    @Path("byobject")
    @Consumes(MediaType.APPLICATION_JSON)
    public String attentionOfficerByObject (Officer officer) {
        Officer inRoom = Optional.ofNullable(officer).filter(o -> null != o.getRange()).orElse(officerObject);
        return attention() + ", officer " + inRoom.getRange();
    }

}
