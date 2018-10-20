package com.udacity.oliverh.movies.data.network;

import java.util.ArrayList;
import java.util.List;

public class QueriedReviewList {
        private int page;
        private int total_results;
        private int total_pages;
        private List<Review> results = new ArrayList<>();

        public QueriedReviewList () {}

        public QueriedReviewList ( int iPage,
                                  int iTotalResults,
                                  int iTotalPages,
                                  List<Review> iResults)  {
            this.page = iPage;
            this.total_results = iTotalResults;
            this.total_pages = iTotalPages;
            this.results = iResults;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getTotal_results() {
            return total_results;
        }

        public void setTotal_results(int total_results) {
            this.total_results = total_results;
        }

        public int getTotal_pages() {
            return total_pages;
        }

        public void setTotal_pages(int total_pages) {
            this.total_pages = total_pages;
        }

        public List<Review> getResults() {
            return results;
        }

        public void setResults(List<Review> results) {
            this.results = results;
        }
}
