package com.mirkopruiti.android_team_test.ui.details

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.chip.Chip
import com.mirkopruiti.android_team_test.R
import com.mirkopruiti.android_team_test.data.model.Pokemon
import com.mirkopruiti.android_team_test.data.model.PokemonInfo
import com.mirkopruiti.android_team_test.ui.details.adapter.StatsAdapter
import com.mirkopruiti.android_team_test.ui.details.state.DetailsState
import com.mirkopruiti.android_team_test.util.setImageWithColorBackground
import io.uniflow.androidx.flow.onStates
import io.uniflow.core.flow.data.UIState
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.error_view.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class DetailActivity : AppCompatActivity() {

    private val detailsViewModel by viewModel<DetailsViewModel>()
    private var pokemon: Pokemon? = null
    private var adapter = StatsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        pokemon = intent.getParcelableExtra<Pokemon>("pokemon")

        setupUI()
        setupSTATE()

        getPokemonDetails()

    }

    private fun getPokemonDetails() {
        lifecycleScope.launch {
            detailsViewModel.getRemotePokemonInfo(pokemon?.name!!, this@DetailActivity)
        }
    }

    private fun setupSTATE() {
        onStates(detailsViewModel) { state ->
            when (state) {
                is UIState.Empty -> onLoading()
                is DetailsState.PokeInfo -> onSuccess(state.pokemonInfo)
                is DetailsState.Error -> onError(state.error)
            }
        }
    }

    private fun onSuccess(pokeInfo: LiveData<PokemonInfo>) {
        if (pokeInfo != null) {

            pokemonImage.setImageWithColorBackground(pokemon?.getImageUrl(), header)

            pokeInfo.observe(this, Observer {
                pokemonName.text = it.name
                setTypes(it.types)
                adapter.setData(it.stats)
                adapter.notifyDataSetChanged()
                onSuccesData()
            })
        }
    }

    private fun onSuccesData() {
        container.visibility = View.VISIBLE
        loading.visibility = View.GONE
        error.visibility = View.GONE
    }

    private fun onError(errorText: String?) {
        Toast.makeText(this@DetailActivity, errorText, Toast.LENGTH_LONG).show()
        container.visibility = View.GONE
        loading.visibility = View.GONE
        error.visibility = View.VISIBLE
    }

    private fun onLoading() {
        container.visibility = View.GONE
        loading.visibility = View.VISIBLE
    }

    private fun setupUI() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        error.tryButton.setOnClickListener { getPokemonDetails() }
        listStats.layoutManager = GridLayoutManager(this, 2);
        listStats.adapter = adapter
    }

    private fun setTypes(types: List<PokemonInfo.TypeResponse>) {
        typeGroup.removeAllViews()
        types.forEach { type ->
            val chip = Chip(this)
            chip.text = type.type.name

            chip.setChipBackgroundColorResource(R.color.colorPrimary)
            chip.setTextColor(ContextCompat.getColor(this, R.color.white))
            chip.isClickable = false
            typeGroup.addView(chip)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}



