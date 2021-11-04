package com.mirkopruiti.android_team_test.data.db

import androidx.paging.PagingSource
import androidx.room.*
import com.mirkopruiti.android_team_test.data.model.Pokemon
import com.mirkopruiti.android_team_test.data.model.PokemonInfo

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonList(pokemonList: List<Pokemon>)

    @Query("SELECT * FROM Pokemon WHERE page = :page_")
    suspend fun getPokemonList(page_: Int): List<Pokemon>

    @Query("SELECT * FROM Pokemon WHERE page = :page_ AND name LIKE '%' || :search || '%'")
    suspend fun getSearchedPokemon(search: String?, page_: Int): List<Pokemon>

    @Query("SELECT * FROM Pokemon")
    fun getPokemonLists(): PagingSource<Int, Pokemon>

    @Query("DELETE FROM Pokemon")
    suspend fun clearPokemons()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonInfo(pokemonInfo: PokemonInfo)

    @Query("SELECT * FROM PokemonInfo WHERE name = :name_")
    suspend fun getPokemonInfo(name_: String): PokemonInfo

}
