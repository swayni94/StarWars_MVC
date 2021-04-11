package com.example.starwarsmvc.controller.activity

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.starwarsmvc.Utilities.*
import com.example.starwarsmvc.adapter.MainRecyclerAdapter
import com.example.starwarsmvc.adapter.interfaceItemClick.IMainItemClick
import com.example.starwarsmvc.databinding.ActivityMainBinding
import com.example.starwarsmvc.model.Results
import com.example.starwarsmvc.service.Repository

class MainActivity : AppCompatActivity(), IMainItemClick {

    private var _binding:ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var repository:Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = Repository()
        repository.fetchFilms()
    }
/* restore
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("Ornek", 1)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getInt("Ornek")
    }
*/
    override fun onResume() {
        super.onResume()

        repository.films.observe(this, {
            setAdapter(it.results)
        })

        binding.mainFragmentSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { text->
                   val datas = repository.searchFunction(text)
                   setAdapter(datas)
                }
                return true
            }
        })

    }

    fun setAdapter(datas : List<Results>){
        val adapter = MainRecyclerAdapter(datas, this)
        binding.mainFragmentRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.mainFragmentRecyclerView.adapter = adapter
    }

    override fun itemClick(url: String, likeCount: String, image: Int, planetId: ArrayList<Int>) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(FILM_URL, url)
        intent.putExtra(FILM_LIKE_COUNT, likeCount)
        intent.putExtra(FILM_IMAGE,image)
        intent.putExtra(PLANET_IDS, planetId)
        startActivity(intent)
    }

}