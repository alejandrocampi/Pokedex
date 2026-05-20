package com.acampif.pokedex.remote

data class PokedexResponse(
    val results: List<PokemonResult>
)

data class PokemonResult(
    val name: String,
    val url: String
) {
    fun getIdFromUrl(): Int {
        return url.trimEnd('/').substringAfterLast('/').toInt()
    }
}