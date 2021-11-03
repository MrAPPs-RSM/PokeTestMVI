package com.mirkopruiti.android_team_test.data.db

import androidx.room.TypeConverter
import com.mirkopruiti.android_team_test.data.model.PokemonInfo
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class StatResponseConverter {

    private var moshi = Moshi.Builder().build()

    @TypeConverter
    fun fromString(value: String): List<PokemonInfo.StatResponse>? {
        val listType = Types.newParameterizedType(
            List::class.java,
            PokemonInfo.StatResponse::class.java
        )
        val adapter: JsonAdapter<List<PokemonInfo.StatResponse>> = moshi.adapter(listType)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromInfoStat(type: List<PokemonInfo.StatResponse>?): String {
        val listType = Types.newParameterizedType(
            List::class.java,
            PokemonInfo.StatResponse::class.java
        )
        val adapter: JsonAdapter<List<PokemonInfo.StatResponse>> = moshi.adapter(listType)
        return adapter.toJson(type)
    }

}