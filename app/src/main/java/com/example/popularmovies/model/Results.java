package com.example.popularmovies.model;

import java.util.List;

public class Results {

    private List<Movie> results;

    public Results(List<Movie> results) {
        this.results = results;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
