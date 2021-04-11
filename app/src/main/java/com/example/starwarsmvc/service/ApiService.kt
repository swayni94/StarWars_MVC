package com.example.starwarsmvc.service

import com.example.starwarsmvc.model.Films
import com.example.starwarsmvc.model.Planet
import com.example.starwarsmvc.model.Results
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("films")
    fun getFilms() : Call<Films>

    @GET("planets/")
    fun getPlanets(@Query("page") id:Int) : Call<Planet>

    @GET("{film}")
    fun getFilm(@Path(value="film", encoded = false) url:String) : Call<Results>
}