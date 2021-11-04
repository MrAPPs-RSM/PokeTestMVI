package com.mirkopruiti.android_team_test.data.db

import androidx.room.TypeConverter
import com.mirkopruiti.android_team_test.data.model.Pokemon
import com.squareup.moshi.Moshi

class FavoritePokemonConverter {

    private var moshi = Moshi.Builder().build()

    @TypeConverter
    fun fromString(value: String): Pokemon? {
       return moshi.adapter(Pokemon::class.java).fromJson(value)
    }

    @TypeConverter
    fun fromPokemon(data: Pokemon): String {
        return moshi.adapter(Pokemon::class.java).toJson(data)
    }

}