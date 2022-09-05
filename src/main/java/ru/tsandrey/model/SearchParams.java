package ru.tsandrey.model;

public class SearchParams {
    private int idSearch;
    private String searchString;

    public SearchParams(int idSearch, String searchString) {
        this.idSearch = idSearch;
        this.searchString = searchString;
    }

    public SearchParams() {
    }

    public int getIdSearch() {
        return idSearch;
    }

    public void setIdSearch(int idSearch) {
        this.idSearch = idSearch;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
}
