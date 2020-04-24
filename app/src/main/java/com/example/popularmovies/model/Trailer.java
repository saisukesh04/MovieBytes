package com.example.popularmovies.model;

import java.util.List;

public class Trailer {

    private String key;
    private List<Trailer> results;

    public Trailer(){
    }

    public Trailer(String key, List<Trailer> results) {
        this.key = key;
        this.results = results;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Trailer> getResultsTrailer() {
        return results;
    }

    public void setResultsTrailer(List<Trailer> results) {
        this.results = results;
    }
}
