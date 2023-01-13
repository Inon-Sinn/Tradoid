package com.example.tradoid.backend;

import android.net.Uri;

public class PFP {
    private final String pfpPath;

    public PFP(String pfpPath){
        this.pfpPath = pfpPath;
    }

    public Uri getPfpPath() {
        return Uri.parse(this.pfpPath);
    }
}
