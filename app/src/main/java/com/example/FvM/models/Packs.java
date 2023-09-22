package com.example.FvM.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

import org.bson.types.ObjectId;

public class Packs extends RealmObject {
    @PrimaryKey
    private ObjectId _id = new ObjectId();

    private String owner_id;
    @Required
    private String name;

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ObjectId get_id() {
        return _id;
    }
}
