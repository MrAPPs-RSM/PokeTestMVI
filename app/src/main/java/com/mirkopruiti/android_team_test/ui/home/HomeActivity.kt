package com.mirkopruiti.android_team_test.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.mirkopruiti.android_team_test.R
import com.mirkopruiti.android_team_test.data.model.Pokemon
import com.mirkopruiti.android_team_test.ui.details.DetailActivity
import com.mirkopruiti.android_team_test.ui.home.adapter.HomeAdapter
import com.mirkopruiti.android_team_test.ui.home.adapter.PokemonClickListener
import com.mirkopruiti.android_team_test.ui.home.state.HomeState
import com.mirkopruiti.android_team_test.util.NetworkUtil
import io.uniflow.androidx.flow.onStates
import io.uniflow.core.flow.data.UIState
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class HomeActivity : AppCompatActivity(), PokemonClickListener {

    private val homeViewModel by viewModel<HomeViewModel>()
    private var adapter = HomeAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUI()
        setupSTATE()

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

    private fun onError(error: String?) {
        progressBar.visibility = View.GONE
        Toast.makeText(this@HomeActivity, error, Toast.LENGTH_LONG).show()
    }

    private fun onLoading() {
        progressBar.visibility = View.VISIBLE
    }

    private fun setupUI() {
        pokemonRecyclerview.layoutManager = GridLayoutManager(this, 2);
        pokemonRecyclerview.adapter = adapter
    }

}



