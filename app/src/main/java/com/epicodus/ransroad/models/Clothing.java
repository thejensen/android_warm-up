package com.epicodus.ransroad.models;

import org.parceler.Parcel;

/**
 * Created by jensese on 12/9/16.
 */

@Parcel
public class Clothing {
    private String item;
    private String description;
    private String imageURL;
    private String pushId;

    public Clothing() {}

    public Clothing(String item, String description, String imageURL) {
        this.item = item;
        this.description = description;
        this.imageURL = imageURL;
    }

    public String getItem() {
        return item;
    }

    public String getDescription() {
        return description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
