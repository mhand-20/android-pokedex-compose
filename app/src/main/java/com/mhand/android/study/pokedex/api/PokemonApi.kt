package com.mhand.android.study.pokedex.api

import com.mhand.android.study.pokedex.model.PokemonDetail
import com.mhand.android.study.pokedex.model.PokemonResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon")
    suspend fun getAllPokemon(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int
    ): PokemonResult

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(
        @Path("name") name: String
    ): PokemonDetail
}