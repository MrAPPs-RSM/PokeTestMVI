package com.mirkopruiti.android_team_test.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoritePokemon(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "pokemon")
    var pokemon: Pokemon
)