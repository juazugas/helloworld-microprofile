package com.redhat.training.juanzu.helloworld;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import javax.ws.rs.core.MediaType;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.arquillian.DefaultDeployment;

/**
 * @author jzuriaga
 *
 */
@RunWith(Arquillian.class)
@DefaultDeployment(type = DefaultDeployment.Type.WAR)
@RunAsClient
public class SaluteControllerTest {
    
    @Test
    public void testSalute () {
        String result = given().get("data/salute").then().statusCode(equalTo(200)).extract().asString();
        assertEquals("Attention", result);
    }

    @Test
    public void testSaluteOfficer () {
        String result = given().with()
                               .formParam("officer", "Lieutenant")
                               .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                               .post("data/salute")
                               .then()
                               .statusCode(equalTo(200))
                               .extract()
                               .asString();
        assertEquals("Attention, officer Lieutenant", result);
    }

    @Test
    @Ignore
    public void testSaluteOfficerWithoutConfig () {
        String result = given().with()
                               .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                               .post("data/salute")
                               .then()
                               .statusCode(equalTo(200))
                               .extract()
                               .asString();
        assertEquals("Attention, officer in the room", result);
    }
    
    @Test
    public void testSaluteOfficerByConfig () {
        String result = given().with()
                               .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                               .post("data/salute")
                               .then()
                               .statusCode(equalTo(200))
                               .extract()
                               .asString();
        assertEquals("Attention, officer Lieutenant", result);
    }

    @Test
    public void testSaluteOfficerByProperty () {
        String result = given().with()
                               .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                               .post("data/salute/byproperty")
                               .then()
                               .statusCode(equalTo(200))
                               .extract()
                               .asString();
        assertEquals("Attention, officer Lieutenant", result);
    }

    @Test
    public void testSaluteOfficerByObject () {
        String result = given().with()
                               .contentType(MediaType.APPLICATION_JSON)
                               .body("{\"range\":\"Captain\"}")
                               .post("data/salute/byobject")
                               .then()
                               .statusCode(equalTo(200))
                               .extract()
                               .asString();
        assertEquals("Attention, officer Captain", result);
    }


    @Test
    public void testSaluteOfficerByObjectConfig () {
        String result = given().with()
                               .contentType(MediaType.APPLICATION_JSON)
                               .post("data/salute/byobject")
                               .then()
                               .statusCode(equalTo(200))
                               .extract()
                               .asString();
        assertEquals("Attention, officer Colonel", result);
    }
}
