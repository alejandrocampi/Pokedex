package com.acampif.pokedex.repository

import com.acampif.pokedex.model.Pokemon

class PokemonRepository {
    private val listaPokemon = mutableListOf<Pokemon>()

    fun getPokemon(): List<Pokemon>{
        return listaPokemon
    }
}