package com.automatodev.antenado.models;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TvMostPopular {
    
    private int id;
    private String name;
    private String permalink;
    
    @SerializedName("start_date")
    private String startDate;
    
    @SerializedName("end_date")
    private String endDate;
    
    private String country;
    private String network;
    private String status;
    
    @SerializedName("image_thumbnail_path")
    private String urlImage;
    
}
