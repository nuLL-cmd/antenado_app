package com.automatodev.antenado.responses;

import com.automatodev.antenado.models.TvMostPopular;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TvDataSheet {

    @SerializedName("total")
    private int totalResults;

    @SerializedName("page")
    private int page;

    @SerializedName("pages")
    private int totalPages;

    @SerializedName("tv_shows")
    private List<TvMostPopular> tvMostPopulars;

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<TvMostPopular> getTvMostPopulars() {
        return tvMostPopulars;
    }

    public void setTvMostPopulars(List<TvMostPopular> tvMostPopulars) {
        this.tvMostPopulars = tvMostPopulars;
    }
}
