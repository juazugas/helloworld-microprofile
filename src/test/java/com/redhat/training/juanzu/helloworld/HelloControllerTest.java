package com.redhat.training.juanzu.helloworld;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import java.net.URL;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.arquillian.DefaultDeployment;
import org.wildfly.swarm.arquillian.DefaultDeployment.Type;

@RunWith(Arquillian.class)
@DefaultDeployment(type = Type.WAR)
public class HelloControllerTest {

    @ArquillianResource
    private URL url;

    @Test
    @RunAsClient
    public void testGet () {
        WebTarget client = getWebTarget("data/hello");
        Response response = client.request(MediaType.TEXT_PLAIN).get();
        assertEquals(200, response.getStatus());
        assertEquals("Hello World", response.readEntity(String.class));
    }


    private WebTarget getWebTarget (final String endpoint) {
        final Client client = ClientBuilder.newBuilder().build();
        return client.target(this.url.toExternalForm() + endpoint);
    }

    @Test
    @RunAsClient
    public void testGetRestAssured () {
        String result = given().get("data/hello")
                               .then()
                               .statusCode(equalTo(200))
                               .extract()
                               .asString();
        assertEquals("Hello World", result);
    }

}
