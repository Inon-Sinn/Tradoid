package com.example.tradoid.backend;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Dates {
    private final String dateCreated;
    private final String lastSeen;

    public Dates (String dateCreated, String lastSeen) {
        this.dateCreated = dateCreated;
        this.lastSeen = lastSeen;
    }

    public String getDateCreated() {
        try {
            return URLDecoder.decode(this.dateCreated, "UTF-8");
        } catch (UnsupportedEncodingException e){
            return this.dateCreated;
        }
    }

    public String getLastSeen() {
        try {
            return URLDecoder.decode(this.lastSeen, "UTF-8");
        } catch (UnsupportedEncodingException e){
            return this.lastSeen;
        }
    }
}
