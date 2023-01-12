package com.example.tradoid.backend;

import java.util.Arrays;
import java.util.List;

public class StockChart {
    private final List<Price> high;
    private final List<Price> low;
    private final List<Price> open;
    private final List<Price> close;

    public StockChart(Price[] high, Price[] low, Price[] open, Price[] close){
        this.high = Arrays.asList(high);
        this.low = Arrays.asList(low);
        this.open = Arrays.asList(open);
        this.close = Arrays.asList(close);
    }

    public List<Price> getHigh() {
        return high;
    }

    public List<Price> getLow() {
        return low;
    }

    public List<Price> getOpen() {
        return open;
    }

    public List<Price> getClose() {
        return close;
    }
}
