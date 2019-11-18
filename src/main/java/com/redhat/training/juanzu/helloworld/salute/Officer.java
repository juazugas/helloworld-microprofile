package com.redhat.training.juanzu.helloworld.salute;

import javax.json.JsonObject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Officer extends Person {

    private String range;

    public Officer() {
        super();
    }

    public Officer(String range) {
        this.range = range;
    }

    public String getRange () {
        return range;
    }

    public void setRange (String range) {
        this.range = range;
    }

    public static Officer fromRange (String range) {
        return new Officer(range);
    }

    public static Officer from (JsonObject object) {
        Officer officer = Officer.fromRange(object.getString("range", "soldier"));
        officer.setName(object.getString("name", "John Smith"));
        return officer;
    }

    @Override
    public String toString () {
        return range + " " + name;
    }
}
