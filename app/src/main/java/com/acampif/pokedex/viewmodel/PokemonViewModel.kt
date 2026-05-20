package com.acampif.pokedex

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.acampif.pokedex.local.PokemonDatabase
import com.acampif.pokedex.local.PokemonEntity
import com.acampif.pokedex.remote.RetrofitClient
import kotlinx.coroutines.launch

class PokemonViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PokemonRepository(
        PokemonDatabase.getDatabase(application).pokemonDao(),
        RetrofitClient.api
    )

    val pokemonSeleccionado = MutableLiveData<PokemonEntity>()

    private val currentFilter = MutableLiveData<FilterState>(FilterState.All)
    private val currentFavQuery = MutableLiveData<String>("")

    val errorMessage = MutableLiveData<String?>()
    val showLoading = MutableLiveData<Boolean>()

    val pokemons: LiveData<List<PokemonEntity>> = currentFilter.switchMap { filter ->
        when (filter) {
            is FilterState.All -> repository.getAllPokemons()
            is FilterState.Favorites -> repository.getFavoritePokemons()
            is FilterState.Search -> repository.searchPokemons(filter.query)
        }
    }

    val favoritedPokemons: LiveData<List<PokemonEntity>> = currentFavQuery.switchMap { query ->
        if (query.isEmpty()) {
            repository.getFavoritePokemons()
        } else {
            repository.searchFavoritePokemons(query)
        }
    }

    private var isLoading = false
    private var currentOffset = 0
    private val limit = 20

    fun seleccionarPokemon(pokemon: PokemonEntity) {
        pokemonSeleccionado.value = pokemon
        viewModelScope.launch {
            try {
                showLoading.value = true
                val pokemonActualizado = repository.fetchPokemonDetails(pokemon)
                pokemonSeleccionado.value = pokemonActualizado
            } catch (e: Exception) {
                errorMessage.value = "Error al obtener detalles."
            } finally {
                showLoading.value = false
            }
        }
    }

    fun agregarPokemon(id: Int, nombre: String, tipo: String, descripcion: String) {
        viewModelScope.launch {
            val nuevoPokemon = PokemonEntity(
                id = id,
                name = nombre,
                types = tipo,
                description = descripcion
            )
            repository.insertPokemon(nuevoPokemon)
        }
    }

    fun buscarPokemon(texto: String) {
        currentFilter.value = if (texto.isEmpty()) FilterState.All else FilterState.Search(texto)
    }

    fun buscarPokemonFavoritos(texto: String) {
        currentFavQuery.value = texto
    }

    fun toggleFavorite(pokemon: PokemonEntity) {
        pokemon.isFavorite = !pokemon.isFavorite
        viewModelScope.launch { repository.updatePokemon(pokemon) }
    }

    fun deletePokemon(pokemon: PokemonEntity) {
        viewModelScope.launch { repository.deletePokemon(pokemon) }
    }

    fun loadMorePokemons() {
        if (isLoading) return
        isLoading = true
        showLoading.value = true
        viewModelScope.launch {
            try {
                repository.fetchAndSavePokemons(limit, currentOffset)
                currentOffset += limit
            } catch (e: Exception) {
                errorMessage.value = "Error de red al cargar Pokémon."
            } finally {
                isLoading = false
                showLoading.value = false
            }
        }
    }

    fun clearError() {
        errorMessage.value = null
    }

    sealed class FilterState {
        object All : FilterState()
        object Favorites : FilterState()
        data class Search(val query: String) : FilterState()
    }
}