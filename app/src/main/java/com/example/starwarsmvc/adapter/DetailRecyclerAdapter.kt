package com.example.starwarsmvc.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsmvc.databinding.ItemPlanetsBinding
import com.example.starwarsmvc.model.Planet

class DetailRecyclerAdapter(private val planet:HashMap<Int, Planet>,
                            private val planetsID:ArrayList<Int>) : RecyclerView.Adapter<DetailRecyclerAdapter.Holder>() {

    class Holder(private val binding: ItemPlanetsBinding) :RecyclerView.ViewHolder(binding.root){
        fun bindViewHolder(planet:String){
            binding.itemPlanetName.text = "# ${planet}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemPlanetsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val term = planetsID[position]
        val key = getPage(term)
        if(planet.containsKey(key)){
            holder.bindViewHolder(planet.get(key)!!.results.get(term%10).name)
        }
    }

    override fun getItemCount(): Int {
        return planetsID.size
    }

    fun getPage(i:Int):Int{
        val result: Int
        if (i<=10){
            result = 1
        }else if (i>10 && i<=20){
            result = 2
        }else if (i>20 && i<=30){
            result = 3
        }else if(i>30 && i<=40){
            result = 4
        }else if (i>40 && i<=50){
            result = 5
        }else if (i>50 && i<=60){
            result = 6
        }
        else{
            result = -1
        }
        return result;
    }
}