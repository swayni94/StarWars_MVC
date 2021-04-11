package com.example.starwarsmvc.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsmvc.R
import com.example.starwarsmvc.adapter.interfaceItemClick.IMainItemClick
import com.example.starwarsmvc.databinding.ItemFilmLayoutBinding
import com.example.starwarsmvc.model.Results
import com.squareup.picasso.Picasso
import kotlin.random.Random

class MainRecyclerAdapter (private val datas:List<Results>,private val listener:IMainItemClick) : RecyclerView.Adapter<MainRecyclerAdapter.Holder>() {

    class Holder(private val binding:ItemFilmLayoutBinding) : RecyclerView.ViewHolder(binding.root){

        fun bindViewHolder(title:String, url:Int, likeCount:String, releaseDate:String, director:String){
            binding.itemFilmTitle.text = title
            binding.itemLikeCount.text = likeCount
            binding.itemFilmReleasedate.text = releaseDate
            binding.itemFilmDirector.text=director
            Picasso.get().load(url).resize(300,300).into(binding.itemFilmImageview)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemFilmLayoutBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        var image=0
        when(position)
        {
            0 -> {
                image = R.drawable.film1
            }
            1 -> {
                image = R.drawable.film2_
            }
            2 -> {
                image = R.drawable.film3
            }
            3 -> {
                image = R.drawable.film4
            }
            4 -> {
                image = R.drawable.film5
            }
            5 -> {
                image = R.drawable.film6
            }
        }
        val likecount = Random.nextInt(700,1000).toString()+ " beğenme"
        holder.bindViewHolder(datas[position].title, image, likecount,
                datas[position].release_date.replace("-","."), datas[position].director)

        holder.itemView.setOnClickListener {
            val term = getPlanetsID(datas[position].planets)
            listener.itemClick(datas[position].url.substring(21), likecount, image, term)
        }
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    fun getPlanetsID(planets:List<String>):ArrayList<Int>{
        val result = ArrayList<Int>()
        for (planet in planets){
            val i=planet.substring(29).replace("/","").toInt()
            result.add(i)
        }
        return result
    }
}