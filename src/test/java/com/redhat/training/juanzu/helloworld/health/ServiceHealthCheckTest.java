package com.redhat.training.juanzu.helloworld.health;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import javax.ws.rs.core.MediaType;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.arquillian.DefaultDeployment;

@RunWith(Arquillian.class)
@DefaultDeployment(type = DefaultDeployment.Type.WAR)
@RunAsClient
public class ServiceHealthCheckTest {

    @Test
    public void testHealth () {
        given().get("health")
               .then()
               .statusCode(equalTo(200))
               .and()
               .contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void testHealthAlive () {
        given().get("health")
               .then()
               .statusCode(200)
               .and()
               .contentType(MediaType.APPLICATION_JSON)
               .and()
               .body("outcome", equalTo("UP"));
    }

}
