package com.acampif.pokedex.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.acampif.pokedex.model.Pokemon
import com.acampif.pokedex.repository.PokemonRepository

class PokemonViewModel : ViewModel(){
    private val repository = PokemonRepository()

    val pokemon = MutableLiveData<List<Pokemon>>()
    val pokemonSeleccionado = MutableLiveData<Pokemon>()
}