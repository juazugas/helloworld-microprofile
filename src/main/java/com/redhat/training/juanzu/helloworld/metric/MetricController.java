package com.redhat.training.juanzu.helloworld.metric;

import java.util.Random;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Metric;
import org.eclipse.microprofile.metrics.annotation.Timed;

@Path("/metric")
@ApplicationScoped
public class MetricController {

    public static final double RATIO_VALUE = Double.MAX_VALUE;

    @Inject
    @Metric(name = "endpoint_counter", absolute = true)
    private Counter counter;

    @Path("timed")
    @Timed(name = "timed-request")
    @GET
    public String timedRequest() {
        // Demo, not production style
        int wait = new Random().nextInt(1000);
        try {
            Thread.sleep(wait);
        } catch (InterruptedException e) {
            // Demo
            e.printStackTrace();
        }

        return "Request is used in statistics, check with the Metrics call.";
    }

    @Path("increment")
    @POST
    @Counted(name = "post_increment", absolute = true, monotonic = true)
    public long doPOSTIncrement () {
        return 1L;
    }


    @Path("increment")
    @GET
    public long doIncrement() {
        counter.inc();
        return counter.getCount();
    }

    @Gauge(name = "counter_gauge", unit = MetricUnits.NONE, absolute = true)
    public long getCustomerCount () {
        return counter.getCount();
    }

    @Gauge(name = "ratio_gauge", unit = MetricUnits.BYTES, absolute = true)
    public double getGaugeRatio () {
        return RATIO_VALUE;
    }
}
