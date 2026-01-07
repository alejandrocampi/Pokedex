package com.acampif.pokedex.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.acampif.pokedex.model.Pokemon
import com.acampif.pokedex.repository.PokemonRepository

class PokemonViewModel : ViewModel(){
    private val repository = PokemonRepository()

    val pokemon = MutableLiveData<List<Pokemon>>()
    val pokemonSeleccionado = MutableLiveData<Pokemon>()

    init{
        obtenerPokemon()
    }

    fun obtenerPokemon(){
        pokemon.value = repository.getPokemon()
    }

    fun eliminarPokemon(position: Int){
        val listaActual = pokemon.value ?: return
        val eliminado = listaActual[position]

        repository.eliminarPokemon(eliminado)
        pokemon.value = repository.getPokemon()
    }

    fun seleccionarPokemon(pokemon:Pokemon){
        pokemonSeleccionado.value = pokemon
    }

    fun cambiarFavorito(pokemon: Pokemon){
        pokemon.favorito = !pokemon.favorito
        repository.cambiarFavorito(pokemon)
        this.pokemon.value = this.pokemon.value
    }

    fun buscarPokemon(texto: String){
        val listaCompleta = repository.getPokemon()
        if(texto.isEmpty()){
            pokemon.value = listaCompleta
        } else {
            pokemon.value = listaCompleta.filter{
                it.nombre.contains(texto, ignoreCase = true)
            }
        }
    }

    fun obtenerFavoritos(){
        pokemon.value = repository.getPokemon().filter {
            it.favorito
        }
    }

    fun buscarPokemonFavoritos(texto: String){
        val favoritos = repository.getPokemon().filter { it.favorito }
        if(texto.isEmpty()){
            pokemon.value = favoritos
        } else {
            pokemon.value = favoritos.filter{
                it.nombre.contains(texto, ignoreCase = true)
            }
        }
    }

}