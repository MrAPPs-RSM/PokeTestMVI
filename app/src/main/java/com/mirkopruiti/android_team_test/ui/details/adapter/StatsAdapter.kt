package com.mirkopruiti.android_team_test.ui.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mirkopruiti.android_team_test.R
import com.mirkopruiti.android_team_test.data.model.PokemonInfo


class StatsAdapter : RecyclerView.Adapter<StatsAdapter.TypeViewHolder>() {

    private var list: List<PokemonInfo.StatResponse> = emptyList()

    fun setData(stats: List<PokemonInfo.StatResponse>) {
        list = stats
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TypeViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: TypeViewHolder, position: Int) {
        val stat: PokemonInfo.StatResponse = list[position]
        holder.bind(stat)
    }

    override fun getItemCount(): Int = list.size

    inner class TypeViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.stat_item, parent, false)) {

        private var pokemonStat: TextView? = null
        private var pokemonStatValue: TextView? = null

        init {
            pokemonStat = itemView.findViewById(R.id.pokemonStat)
            pokemonStatValue = itemView.findViewById(R.id.pokemonStatValue)
        }

        fun bind(stat: PokemonInfo.StatResponse) {
            pokemonStat?.text = stat.stat.name
            pokemonStatValue?.text = stat.baseStat.toString()
        }

    }

}