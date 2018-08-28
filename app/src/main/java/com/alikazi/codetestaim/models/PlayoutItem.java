package com.alikazi.codetestaim.models;

import java.util.List;

public class PlayoutItem {

    public String artist;
    public String duration;
    public String imageUrl;
    public String previewUrl;
    public String status;
    public String subtitle;
    public String time;
    public String title;
    public String type;
    public List<CustomField> customFields;

    private class CustomField {
        public String name;
        public String value;
    }
}
