package com.mobillium.bukoliandroidsdk.models;

/**
 * Created by oguzhandongul on 11/05/16.
 */
public class Matching {
    private int length;
    private int offset;

    public Matching(int length, int offset) {
        this.length = length;
        this.offset = offset;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
