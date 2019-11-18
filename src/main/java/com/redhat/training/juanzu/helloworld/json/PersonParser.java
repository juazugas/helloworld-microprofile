package com.redhat.training.juanzu.helloworld.json;

import javax.inject.Named;
import javax.json.JsonObject;
import com.redhat.training.juanzu.helloworld.salute.Person;

@Named
public class PersonParser implements JsonParser<Person> {

    public Person doParse (final JsonObject jsonObject) {
        return Person.fromJson(jsonObject);
    }

}
