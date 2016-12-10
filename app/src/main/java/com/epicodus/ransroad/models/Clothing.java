package com.epicodus.ransroad.models;

import org.parceler.Parcel;

/**
 * Created by jensese on 12/9/16.
 */

@Parcel
public class Clothing {
    private String item;
    private String pushId;

    public Clothing() {}

    public Clothing(String item) {
        this.item = item;
    }

    public String getItem() {
        return item;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
