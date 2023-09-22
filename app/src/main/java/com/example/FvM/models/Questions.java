package com.example.FvM.models;

import org.bson.types.ObjectId;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Questions extends RealmObject {
    @PrimaryKey
    private ObjectId _id = new ObjectId();

    private String owner_id;
    @Required
    private String question;
    @Required
    private String category = String.valueOf(Category.values()[0]);
    @Required
    private ObjectId pack_id;

    public void setPack_id(ObjectId pack_id) {
        this.pack_id = pack_id;
    }

    public ObjectId getPack_id() {
        return pack_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

}
