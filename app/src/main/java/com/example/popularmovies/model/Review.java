package com.example.popularmovies.model;

import java.util.List;

public class Review {

    private String content;
    private String author;
    private List<Review> results;

    public Review(){
    }

    public Review(String content, String author, List<Review> results) {
        this.content = content;
        this.author = author;
        this.results = results;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<Review> getResultsReview() {
        return results;
    }

    public void setResultsReview(List<Review> results) {
        this.results = results;
    }
}
