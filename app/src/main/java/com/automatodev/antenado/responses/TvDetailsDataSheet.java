package com.automatodev.antenado.responses;

import com.automatodev.antenado.models.TvDetails;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TvDetailsDataSheet {

    private TvDetails tvShow;

    public void setTvShow(TvDetails tvDetails){
        this.tvShow = tvDetails;
    }

    public TvDetails getTvShow(){
        return this.tvShow;
    }
}
