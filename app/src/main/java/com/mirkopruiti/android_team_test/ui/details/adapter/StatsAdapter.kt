package com.mirkopruiti.android_team_test.ui.details.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mirkopruiti.android_team_test.R
import com.mirkopruiti.android_team_test.data.model.PokemonInfo
import com.skydoves.progressview.ProgressView


class StatsAdapter(context: Context) : RecyclerView.Adapter<StatsAdapter.TypeViewHolder>() {

    private var list: List<PokemonInfo.StatResponse> = emptyList()
    private var context : Context = context


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
        private var pokemonStatProgress: ProgressView? = null

        init {
            pokemonStat = itemView.findViewById(R.id.pokemonStat)
            pokemonStatProgress = itemView.findViewById(R.id.progressStat)
        }

        fun bind(stat: PokemonInfo.StatResponse) {
            pokemonStat?.text = stat.stat.name.replace("-", " ").replaceFirstChar { it.uppercaseChar() }
            pokemonStatProgress?.progress = stat.baseStat.toFloat()
            pokemonStatProgress?.highlightView?.color = ContextCompat.getColor(context, getTypeColor(stat.stat.name))
            pokemonStatProgress?.labelText = stat.baseStat.toString()
        }

    }

}

fun getTypeColor(type: String): Int {
    return when (type) {
        "hp" -> R.color.hp
        "attack" -> R.color.attack
        "defense" -> R.color.defense
        "special-attack" -> R.color.special_attack
        "special-defense" -> R.color.special_defense
        "speed" -> R.color.speed
        "accuracy" -> R.color.colorPrimaryDark
        "evasion" -> R.color.colorPrimaryDark
        else -> R.color.colorPrimaryDark
    }
}