package com.example.starwarsmvc.controller.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.starwarsmvc.Utilities.FILM_IMAGE
import com.example.starwarsmvc.Utilities.FILM_LIKE_COUNT
import com.example.starwarsmvc.Utilities.FILM_URL
import com.example.starwarsmvc.Utilities.PLANET_IDS
import com.example.starwarsmvc.adapter.DetailRecyclerAdapter
import com.example.starwarsmvc.databinding.ActivityDetailBinding
import com.example.starwarsmvc.model.Planet
import com.example.starwarsmvc.service.Repository
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    private var _binding:ActivityDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val filmurl:String = intent.getStringExtra(FILM_URL).toString()
        val filmcount:String = intent.getStringExtra(FILM_LIKE_COUNT).toString()
        val filmImage:Int = intent.getIntExtra(FILM_IMAGE, 0)
        val planetIds:ArrayList<Int> = intent.getIntegerArrayListExtra(PLANET_IDS) as ArrayList<Int>
        repository = Repository()

        repository.fetchFilm(filmurl)

        repository.fetchPlanetPage(planetIds)

        repository.film.observe(this, {
            Picasso.get().load(filmImage).resize(300,300).into(binding.fragmentDetailImageView)
            binding.filmName.text = it.title
            binding.releaseDate.text = it.release_date.replace("-",".")
            binding.directorName.text = it.director
            binding.likeCount.text = filmcount
            binding.fragmentDetailDescreption.text = it.opening_crawl

        })

        repository.planetPages.observe(this, {
            setAdapter(it, planetIds)
        })

    }


    fun setAdapter(planet:HashMap<Int, Planet>, planetIds:ArrayList<Int>){
        val adapter = DetailRecyclerAdapter(planet, planetIds)
        binding.fragmentDetailRecyclerview.layoutManager = GridLayoutManager(this, 3)
        binding.fragmentDetailRecyclerview.adapter = adapter
    }

    fun backActivity(view:View){
        finish()
       // val intent = Intent(this, MainActivity::class.java)
      //  startActivity(intent)
    }
}