package com.example.tradoid.backend;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class History {
    private final String[] history;

    public History (String[] history) {
        this.history = history;
    }

    public List<String[]> getHistoryFormatted() {
        List<String[]> historyFormatted = new ArrayList<>();
        for (String event : this.history){
            String eventDecoded;
            try {
                eventDecoded = URLDecoder.decode(event, "UTF-8");
            } catch (UnsupportedEncodingException e){
                eventDecoded = event;
            }
            String[] eventFormatted = eventDecoded.split(",");
            historyFormatted.add(eventFormatted);
        }
        return historyFormatted;
    }
}
