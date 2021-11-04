package com.mirkopruiti.android_team_test.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.mirkopruiti.android_team_test.R
import com.mirkopruiti.android_team_test.data.model.FavoritePokemon
import com.mirkopruiti.android_team_test.data.model.Pokemon
import com.mirkopruiti.android_team_test.ui.details.DetailActivity
import com.mirkopruiti.android_team_test.ui.favorites.FavoritesActivity
import com.mirkopruiti.android_team_test.ui.home.adapter.HomeAdapter
import com.mirkopruiti.android_team_test.ui.home.adapter.PokemonClickListener
import com.mirkopruiti.android_team_test.ui.home.state.HomeState
import com.mirkopruiti.android_team_test.util.NetworkUtil
import io.uniflow.androidx.flow.onStates
import io.uniflow.core.flow.data.UIState
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class HomeActivity : AppCompatActivity(), PokemonClickListener {

    private val homeViewModel by viewModel<HomeViewModel>()
    private lateinit var adapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUI()
        setupSTATE()

    }

    override fun onResume() {
        fetchData()
        super.onResume()
    }

    private fun fetchData() {
        if (NetworkUtil.isConnectedToNetwork(this)) {
            homeViewModel.getRemotePokemons()
        } else {
            homeViewModel.getLocalPokemons()
            Toast.makeText(this@HomeActivity, "No Connection - Results from Local DB", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupSTATE() {
        onStates(homeViewModel) { state ->
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
                adapter.submitData(pagingData)
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
        homeViewModel.setFavorites(poke.isFavorite, favoritePokemon)
    }

    private fun onError(error: String?) {
        progressBar.visibility = View.GONE
        Toast.makeText(this@HomeActivity, error, Toast.LENGTH_LONG).show()
    }

    private fun onLoading() {
        progressBar.visibility = View.VISIBLE
    }

    private fun setupUI() {
        supportActionBar?.elevation = 0F
        adapter = HomeAdapter(this)
        pokemonRecyclerview.layoutManager = GridLayoutManager(this, 2);
        pokemonRecyclerview.adapter = adapter
        performSearch()
    }

    private fun performSearch() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                search(newText)
                return true
            }
        })
    }

    private fun search(text: String?) {
        if (text?.length!! > 0)
            homeViewModel.getSearchPokemons(text!!)
        else
            fetchData()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) : Boolean {

        val id = item.itemId

        if (id == R.id.action_favorite) {
            var intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
            return true
        }

        return super.onOptionsItemSelected(item)
    }


}



