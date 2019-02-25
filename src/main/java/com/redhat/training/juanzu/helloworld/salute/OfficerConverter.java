package com.redhat.training.juanzu.helloworld.salute;

import java.io.IOException;
import org.eclipse.microprofile.config.spi.Converter;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OfficerConverter implements Converter<Officer> {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public Officer convert (String value) {
        if (null == value || value.trim().equals("")) {
            return null;
        }
        try {
            return mapper.readValue(value, Officer.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
