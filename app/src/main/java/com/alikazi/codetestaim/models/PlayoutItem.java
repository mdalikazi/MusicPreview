package com.alikazi.codetestaim.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PlayoutItem {
    @SerializedName("@album")
    public String album;
    @SerializedName("@artist")
    public String artist;
    @SerializedName("@cart")
    public String cart;
    @SerializedName("@duration")
    public String duration;
    @SerializedName("@id")
    public String id;
    @SerializedName("@imageUrl")
    public String imageUrl;
    @SerializedName("@previewUrl")
    public String previewUrl;
    @SerializedName("@status")
    public String status;
    @SerializedName("@subtitle")
    public String subtitle;
    @SerializedName("@time")
    public String time;
    @SerializedName("@title")
    public String title;
    @SerializedName("@type")
    public String type;
    public boolean isPlaying;
    public ArrayList<CustomField> customFields;

    public class CustomField {
        @SerializedName("@name")
        public String name;
        @SerializedName("@value")
        public String value;
    }
}
