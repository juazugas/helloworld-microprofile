package com.redhat.training.juanzu.helloworld.json;

import javax.inject.Named;
import javax.json.JsonObject;
import com.redhat.training.juanzu.helloworld.salute.Officer;

/**
 * Parses Officer from String.
 * 
 * @author jzuriaga
 *
 */
@Named
public class OfficerParser implements JsonParser<Officer> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Officer doParse (JsonObject object) {
        return Officer.from(object);
    }

}
