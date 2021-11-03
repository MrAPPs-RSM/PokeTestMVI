package com.mirkopruiti.android_team_test.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mirkopruiti.android_team_test.R
import com.mirkopruiti.android_team_test.data.model.Pokemon
import com.mirkopruiti.android_team_test.ui.home.adapter.PokemonClickListener
import com.mirkopruiti.android_team_test.util.GlideApp
import com.mirkopruiti.android_team_test.util.setImageWithColorBackground

class HomeAdapter(private val pokemonClickListener: PokemonClickListener) : PagingDataAdapter<Pokemon, PokemonViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bindTo(getItem(position), holder)
        holder.itemView.setOnClickListener {
            getItem(position)?.let { it -> pokemonClickListener.onPokemonClickListener(it) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonViewHolder {
        return PokemonViewHolder(parent)
    }

}


class PokemonViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.home_item, parent, false)) {

    private val pokemonCard = itemView.findViewById<CardView>(R.id.pokemonCard)
    private val pokemonImage = itemView.findViewById<ImageView>(R.id.pokemonImage)
    private val pokemonName = itemView.findViewById<TextView>(R.id.pokemonName)

    fun bindTo(poke: Pokemon?, holder: PokemonViewHolder) {

        val pokemon = poke

        pokemonCard.setCardBackgroundColor(
            ContextCompat.getColor(holder.itemView?.context,
                R.color.gray_dark
            )
        )

        pokemonImage.setImageWithColorBackground(pokemon?.getImageUrl(), pokemonCard)

        pokemonName.text = pokemon?.name.orEmpty()

    }
}