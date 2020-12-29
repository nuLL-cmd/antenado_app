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



}
