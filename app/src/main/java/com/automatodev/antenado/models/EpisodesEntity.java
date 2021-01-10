package com.automatodev.antenado.models;

import com.google.gson.annotations.SerializedName;

public class EpisodesEntity {

    private int season;
    private int episode;
    private String name;

    @SerializedName("air_date")
    private String airDate;


    public void setSeason(int season){
        this.season = season;
    }

    public int getSeason(){
        return this.season;
    }

    public void setEpisode(int episode){
        this.episode = episode;
    }

    public int getEpisode(){
        return this.episode;
    }

    public void setAirDate(String airDate){
        this.airDate = airDate;
    }

    public String getAirDate(){
        return this.airDate;
    }
}
