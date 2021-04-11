package com.example.starwarsmvc.model

import com.google.gson.annotations.SerializedName

data class Results(
    @SerializedName("title") val title : String,
    @SerializedName("episode_id") val episode_id : Int,
    @SerializedName("opening_crawl") val opening_crawl : String,
    @SerializedName("director") val director : String,
    @SerializedName("producer") val producer : String,
    @SerializedName("release_date") val release_date : String,
    @SerializedName("characters") val characters : List<String>,
    @SerializedName("planets") val planets : List<String>,
    @SerializedName("starships") val starships : List<String>,
    @SerializedName("vehicles") val vehicles : List<String>,
    @SerializedName("species") val species : List<String>,
    @SerializedName("created") val created : String,
    @SerializedName("edited") val edited : String,
    @SerializedName("url") val url : String
)
