package com.example.tradoid.backend;

public class Response {
    private final boolean passed;
    private final Object data;

    public Response(boolean passed, Object data){
        this.passed = passed;
        this.data = data;
    }

    public boolean passed(){
        return this.passed;
    }

    public String getData(){
        return this.data.toString();
    }
}
