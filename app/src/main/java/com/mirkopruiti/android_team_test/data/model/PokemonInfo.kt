package com.mirkopruiti.android_team_test.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class PokemonInfo(
    @field:Json(name = "id") @PrimaryKey val id: Int,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "height") val height: Int,
    @field:Json(name = "weight") val weight: Int,
    @field:Json(name = "base_experience") val experience: Int,
    @field:Json(name = "types") val types: List<TypeResponse>,
    @field:Json(name = "stats") val stats: List<StatResponse>
) {

    fun getWeightString(): String = String.format("%.1f KG", weight.toFloat() / 10)
    fun getHeightString(): String = String.format("%.1f M", height.toFloat() / 10)

    @JsonClass(generateAdapter = true)
    data class TypeResponse(
        @field:Json(name = "slot") val slot: Int,
        @field:Json(name = "type") val type: Type
    )

    @JsonClass(generateAdapter = true)
    data class Type(
        @field:Json(name = "name") val name: String
    )

    @JsonClass(generateAdapter = true)
    data class StatResponse(
        @field:Json(name = "base_stat") val baseStat: Int,
        @field:Json(name = "stat") val stat: Stat
    )

    @JsonClass(generateAdapter = true)
    data class Stat(
        @field:Json(name = "name") val name: String
    )

}