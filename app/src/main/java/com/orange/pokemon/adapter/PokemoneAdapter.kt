package com.orange.pokemon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.orange.pokemon.R
import com.orange.pokemon.data.PokemonEntity
import com.orange.pokemon.databinding.ItempokemoneBinding


class PokemoneAdapter :ListAdapter<PokemonEntity, PokemoneAdapter.pokemoneViewHolder>(pokemoneDiffUtils()){


    inner class pokemoneViewHolder(val binding : ItempokemoneBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemone: PokemonEntity) {
            val context=itemView.context
            val id = "id"
            with(binding) {
                pokemoneName.text = pokemone.name
                pokemoneEvolvedfrom.text = pokemone.evolvedfrom
                pokemoneReason.text = pokemone.reason
                Glide.with(context)
                    .load(pokemone.imageurl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(binding.imageView)

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemoneAdapter.pokemoneViewHolder {
        val binding = ItempokemoneBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return pokemoneViewHolder(binding)
    }

    override fun onBindViewHolder(holder: pokemoneViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


}

class pokemoneDiffUtils : DiffUtil.ItemCallback<PokemonEntity>(){
    override fun areItemsTheSame(oldItem: PokemonEntity, newItem: PokemonEntity): Boolean {
        TODO("Not yet implemented")
    }

    override fun areContentsTheSame(oldItem: PokemonEntity, newItem: PokemonEntity): Boolean {
        TODO("Not yet implemented")
    }

}