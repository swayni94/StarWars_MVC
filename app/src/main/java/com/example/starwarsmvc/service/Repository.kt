package com.example.starwarsmvc.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.starwarsmvc.model.Films
import com.example.starwarsmvc.model.Planet
import com.example.starwarsmvc.model.Results
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class Repository {
    private val BASE_URL="https://swapi.dev/api/"
    private val service:ApiService

    private var _films = MutableLiveData<Films>()
    val films:LiveData<Films> = _films


    private val _film = MutableLiveData<Results>()
    val film:LiveData<Results> get() = _film

    private val _planetsPages=MutableLiveData<HashMap<Int, Planet>>()
    val planetPages:LiveData<HashMap<Int, Planet>> get() = _planetsPages

    init{
        service = getApiService()
    }

    private fun getApiService():ApiService{
        val client = OkHttpClient.Builder().readTimeout(1200, TimeUnit.SECONDS).connectTimeout(1200,TimeUnit.SECONDS).build()
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).client(client).build()
        return retrofit.create(ApiService::class.java)
    }

    fun fetchFilms(){
        service.getFilms().enqueue(object : Callback<Films>{
            override fun onResponse(call: Call<Films>, response: Response<Films>) {
                if (response.isSuccessful){
                    _films.postValue(response.body())
                }
            }
            override fun onFailure(call: Call<Films>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun fetchFilm(url:String){
        service.getFilm(url).enqueue(object :Callback<Results>{
            override fun onResponse(call: Call<Results>, response: Response<Results>) {
                if (response.isSuccessful){
                    _film.postValue(response.body())
                }
            }
            override fun onFailure(call: Call<Results>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun fetchPlanetPage(ids:ArrayList<Int>){
        val planetPage = HashMap<Int,Planet>()
        val keys = getPage(ids)
        var i = 0;
        for (key in keys){
            if (i<key){
                i=key
                service.getPlanets(key).enqueue(object : Callback<Planet> {
                    override fun onResponse(call: Call<Planet>, response: Response<Planet>) {
                        if (response.isSuccessful) {
                            planetPage[key] = response.body()!!
                            _planetsPages.postValue(planetPage)
                        }
                    }
                    override fun onFailure(call: Call<Planet>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
            }
        }
    }
    fun getPage(ids: ArrayList<Int>):ArrayList<Int>{
        val result = ArrayList<Int>()
        for (i in ids){
            if (i<=10){
                result.add(1)
            }else if (i>10 && i<=20){
                result.add(2)
            }else if (i>20 && i<=30){
                result.add(3)
            }else if(i>30 && i<=40){
                result.add(4)
            }else if (i>40 && i<=50){
                result.add(5)
            }else if (i>50 && i<=60){
                result.add(6)
            }
            else{
                result.add(1)
            }
        }
        return result;
    }

    fun searchFunction(search:String):ArrayList<Results>{
        val results = ArrayList<Results>()
        films.value!!.results.forEach {
            if (it.title.toLowerCase(Locale.ROOT).contains(search)){
                results.add(it)
            }
        }
        return results
    }
}