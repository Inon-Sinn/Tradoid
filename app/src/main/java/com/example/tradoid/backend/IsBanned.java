package com.example.tradoid.backend;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class IsBanned {
    private final boolean isBanned;
    private final String reason;

    public IsBanned(boolean isBanned, String reason){
        this.isBanned = isBanned;
        this.reason = reason;
    }

    public boolean getIsBanned(){
        return this.isBanned;
    }

    public String getReason() {
        try {
            return URLDecoder.decode(this.reason, "UTF-8");
        } catch (UnsupportedEncodingException e){
            return this.reason;
        }
    }
}
