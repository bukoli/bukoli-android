package com.mobillium.bukoliandroidsdk.models;

import java.util.ArrayList;

/**
 * Created by oguzhandongul on 11/05/16.
 */
public class AutoCompleteItem {
    private String id;
    private String name;
    private ArrayList<Matching> matches = new ArrayList<>();

    public AutoCompleteItem(String id, String name, ArrayList<Matching> matches) {
        this.id = id;
        this.name = name;
        this.matches = matches;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Matching> getMatches() {
        return matches;
    }

    public void setMatches(ArrayList<Matching> matches) {
        this.matches = matches;
    }
}
