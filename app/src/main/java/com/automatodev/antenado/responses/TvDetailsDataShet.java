package com.automatodev.antenado.responses;

import com.automatodev.antenado.models.EpisodesEntity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvDetailsDataShet {
    private long id;
    private String name;
    private String permalink;
    private String description;

    @SerializedName("start_date")
    private String startDate;

    private String status;
    private String netWork;
    private String country;

    List<EpisodesEntity> episodes;
    List<String> pictures;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<EpisodesEntity> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<EpisodesEntity> episodes) {
        this.episodes = episodes;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    private void setId(long id){
        this.id = id;
    }

    private void setName(String name){
        this.name = name;
    }

    private void setPermalink(String permalink){
        this.permalink = permalink;
    }

    private void setDescription(String description){
        this.description = description;
    }

    private void setStartDate(String startDate){
        this.startDate = startDate;
    }

    private void setCountry(String country){
        this.country = country;
    }

    private void setNetWork(String netWork){
        this.netWork = netWork;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPermalink() {
        return permalink;
    }

    public String getDescription() {
        return description;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getNetWork() {
        return netWork;
    }

    public String getCountry() {
        return country;
    }
}
