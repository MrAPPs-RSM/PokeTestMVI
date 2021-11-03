package com.mirkopruiti.android_team_test.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
@JsonClass(generateAdapter = true)
data class Pokemon(
    var page: Int = 0,
    var id: Int = 0,
    @field:Json(name = "name") @PrimaryKey val name: String,
    @field:Json(name = "url") val url: String
) : Parcelable {

    fun setPokemonPage(pg: Int) {
        page = pg
    }

    fun setPokemonId() {
        id = getPokemonId()
    }

    fun getPokemonId(): Int {
        return url.split("/".toRegex()).dropLast(1).last().toInt()
    }

    fun getImageUrl(): String {
        val index = url.split("/".toRegex()).dropLast(1).last()
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$index.png"
    }
}