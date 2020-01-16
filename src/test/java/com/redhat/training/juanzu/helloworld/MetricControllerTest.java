package com.redhat.training.juanzu.helloworld;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.closeTo;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.arquillian.DefaultDeployment;
import com.redhat.training.juanzu.helloworld.metric.MetricController;
import io.restassured.http.ContentType;

/**
 * @author jzuriaga
 *
 */
@RunWith(Arquillian.class)
@DefaultDeployment(type = DefaultDeployment.Type.WAR)
@RunAsClient
public class MetricControllerTest {

    private static final double RATIO_VALUE = MetricController.RATIO_VALUE;

    @Test
    public void testCountIncrement () {
        String countResult = given().accept(ContentType.TEXT).get("data/metric/increment").thenReturn().asString();

        given().accept(ContentType.JSON)
               .get("/metrics/application/counter_gauge")
               .then()
               .statusCode(200)
               .and()
               .contentType(ContentType.JSON)
               .and()
               .body("counter_gauge", equalTo(Integer.valueOf(countResult)));
    }

    @Test
    public void testRatio () {
        activateApplicationMetrics();

        given().accept(ContentType.JSON)
               .get("/metrics/application/ratio_gauge")
               .then()
               .statusCode(200)
               .and()
               .contentType(ContentType.JSON)
               .and()
               .body("ratio_gauge", closeTo(RATIO_VALUE, 0.001));
    }

    private String activateApplicationMetrics () {
        return given().accept(ContentType.TEXT).get("data/metric/increment").thenReturn().asString();
    }

}
