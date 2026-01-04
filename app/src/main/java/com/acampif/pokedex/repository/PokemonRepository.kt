package com.acampif.pokedex.repository

import com.acampif.pokedex.R
import com.acampif.pokedex.model.Pokemon

class PokemonRepository {
    private val listaPokemon = mutableListOf<Pokemon>()

    init{
        listaPokemon.add(Pokemon("Pikachu", R.drawable.pikachu, "Pokémon de tipo eléctrico."))
        listaPokemon.add(Pokemon("Charmander", R.drawable.charmander, "Pokémon de tipo fuego."))
        listaPokemon.add(Pokemon("Squirtle", R.drawable.squirtle, "Pokémon de tipo agua."))
        listaPokemon.add(Pokemon("Bulbasaur", R.drawable.bulbasaur, "Pokémon de tipo planta/veneno."))
    }
    fun getPokemon(): List<Pokemon>{
        return listaPokemon
    }
}