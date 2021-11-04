package com.mirkopruiti.android_team_test.ui.favorites

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.recyclerview.widget.GridLayoutManager
import com.mirkopruiti.android_team_test.R
import com.mirkopruiti.android_team_test.data.model.FavoritePokemon
import com.mirkopruiti.android_team_test.data.model.Pokemon
import com.mirkopruiti.android_team_test.ui.details.DetailActivity
import com.mirkopruiti.android_team_test.ui.favorites.adapter.FavoriteAdapter
import com.mirkopruiti.android_team_test.ui.home.adapter.PokemonClickListener
import com.mirkopruiti.android_team_test.ui.home.state.HomeState
import com.mirkopruiti.android_team_test.util.NetworkUtil
import io.uniflow.androidx.flow.onStates
import io.uniflow.core.flow.data.UIState
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class FavoritesActivity : AppCompatActivity(), PokemonClickListener {

    private val favoriteViewModel by viewModel<FavoriteViewModel>()
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites)

        setupUI()
        setupSTATE()

    }

    override fun onResume() {
        fetchData()
        super.onResume()
    }

    private fun fetchData() {
        favoriteViewModel.getFavoritePokemons()
    }

    private fun setupSTATE() {
        onStates(favoriteViewModel) { state ->
            when (state) {
                is UIState.Loading -> onLoading()
                is HomeState.Pokemons -> onSuccess(state.pokemon)
                is HomeState.Error -> onError(state.error)
            }
        }
    }

    private fun onSuccess(pokes: Flow<PagingData<Pokemon>>) {
        lifecycleScope.launch {
            pokes.collect { pagingData ->
                adapter.submitData(pagingData.filter { it.isFavorite })
            }
        }
        adapter.notifyDataSetChanged()
        progressBar.visibility = View.GONE
    }

    override fun onPokemonClickListener(poke: Pokemon) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("pokemon", poke)
        startActivity(intent)
    }

    override fun onFavoriteClickListener(pos: Int, poke: Pokemon) {
        var favoritePokemon = FavoritePokemon(poke.id, poke)
        favoriteViewModel.setFavorites(favoritePokemon, ::onComplete)
    }

    private fun onComplete() {
        fetchData()
    }

    private fun onError(error: String?) {
        progressBar.visibility = View.GONE
        Toast.makeText(this@FavoritesActivity, error, Toast.LENGTH_LONG).show()
    }

    private fun onLoading() {
        progressBar.visibility = View.VISIBLE
    }

    private fun setupUI() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Favorites"
        adapter = FavoriteAdapter(this)
        pokemonRecyclerview.layoutManager = GridLayoutManager(this, 2);
        pokemonRecyclerview.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }


}