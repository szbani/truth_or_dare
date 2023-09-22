package com.example.FvM.models;

public enum Category {
    Truth("Truth"),
    Dare("Dare");
    String displayName;
    Category(String displayName) {
        this.displayName = displayName;
    }
}
