package com.redhat.training.juanzu.helloworld.dao;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

@ApplicationScoped
@Health
@Named("SalutationLogDAO")
public class SalutationLogDAO implements HealthCheck {

    private final Map<Integer, String> table = new LinkedHashMap<>();

    private final AtomicInteger sequence = new AtomicInteger();

    @Inject
    @ConfigProperty(name = "salute.format", defaultValue = "%s")
    private String saluteFormat;

    public Salute save (String message) {
        Integer id = sequence.incrementAndGet();
        table.put(id, message);
        return Salute.builder().id(id).log(message).build();
    }

    public int size () {
        return table.size();
    }

    public boolean delete (Salute salute) {
        return table.remove(salute.getId()) != null;
    }

    @Override
    public HealthCheckResponse call () {
        int salutations = table.size();
        return HealthCheckResponse.named("salute-log")
                                  .withData("salutations", salutations)
                                  .state(salutations > 0)
                                  .build();
    }
}
