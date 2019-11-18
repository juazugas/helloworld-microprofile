package com.redhat.training.juanzu.helloworld.salute;

import java.util.Optional;
import java.util.function.Predicate;
import org.eclipse.microprofile.config.spi.Converter;
import com.redhat.training.juanzu.helloworld.json.OfficerParser;

public class OfficerConverter implements Converter<Officer> {

	private OfficerParser parser = new OfficerParser();
	private Predicate<String> emptyValue = v -> v.trim().equals("");

    @Override
    public Officer convert (String value) {
		return Optional.ofNullable(value).filter(emptyValue.negate()).map(parser::parse).orElse(null);
    }

}
