package com.example.rickandmorty.presentation.characterlist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.rickandmorty.R
import com.example.rickandmorty.data.model.Result
import com.example.rickandmorty.databinding.ItemCharacterBinding

class CharacterListAdapter : RecyclerView.Adapter<CharacterListAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: ItemCharacterBinding):RecyclerView.ViewHolder(binding.root)

    private lateinit var context: Context

    private val differCallBack = object : DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,  differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context=parent.context
        return ViewHolder(
            ItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = differ.currentList[position]

        holder.binding.apply{
            tvName.text=character.name
            tvSpecies.text=character.species
            tvStatus.text=character.status
            when(character.status){
                 "Alive" ->{
                     ivStatus.setImageResource(R.drawable.ic_status_alive)
                }
                "Dead"->{
                    ivStatus.setImageResource(R.drawable.ic_status_dead)
                }
                "unknown"->{
                    ivStatus.setImageResource(R.drawable.ic_status)
                }
            }
            Glide.with(context).load(character.image)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .thumbnail(0.25F).into(ivCharacterImage)

        }
        holder.itemView.apply {
            setOnClickListener{
                onItemClickListener?.let {res->
                    res(character) }
            }
        }
    }

    override fun getItemCount(): Int =differ.currentList.size

    private var onItemClickListener : ((Result) -> Unit) ?= null

    fun setOnClickListener(listener :  (Result) -> Unit){
        onItemClickListener = listener
    }
}