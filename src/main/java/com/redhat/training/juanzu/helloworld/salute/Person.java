package com.redhat.training.juanzu.helloworld.salute;

import javax.json.JsonObject;

public class Person {

    protected String name;

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    @Override
    public String toString () {

        return name;
    }

    public static Person from (String name) {
        Person persona = new Person();
        persona.setName(name);
        return persona;
    }

    public static Person fromJson (JsonObject object) {
        return Person.from(object.getString("name", "John Smith"));
    }
}
