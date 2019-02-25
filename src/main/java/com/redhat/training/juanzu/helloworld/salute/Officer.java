package com.redhat.training.juanzu.helloworld.salute;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Officer {

    private String range;

    public Officer() {

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

    public static Officer from (String range) {
        return new Officer(range);
    }
}
