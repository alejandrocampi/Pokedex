package com.acampif.pokedex

import androidx.lifecycle.LiveData
import com.acampif.pokedex.local.PokemonDao
import com.acampif.pokedex.local.PokemonEntity
import com.acampif.pokedex.remote.PokeApiService

class PokemonRepository(
    private val pokemonDao: PokemonDao,
    private val apiService: PokeApiService
) {

    suspend fun fetchAndSavePokemons(limit: Int, offset: Int) {
        val response = apiService.getPokemons(limit, offset)
        val entities = response.results.map {
            PokemonEntity(id = it.getIdFromUrl(), name = it.name)
        }
        pokemonDao.insertAll(entities)
    }

    suspend fun fetchPokemonDetails(pokemon: PokemonEntity): PokemonEntity {
        if (pokemon.description.isNotEmpty()) return pokemon
        return try {
            val detail = apiService.getPokemonDetail(pokemon.id)
            val typeList = detail.types.joinToString(",") { it.type.name }
            val species = apiService.getPokemonSpecies(pokemon.id)
            val descripcion = species.flavorTextEntries
                .firstOrNull { it.language.name == "es" }?.flavorText
                ?: species.flavorTextEntries.firstOrNull { it.language.name == "en" }?.flavorText
                ?: "Sin descripción disponible"
            val descripcionLimpia = descripcion.replace("\n", " ").replace("\r", " ")

            val updatedPokemon = pokemon.copy(
                types = typeList,
                description = descripcionLimpia
            )
            pokemonDao.update(updatedPokemon)
            updatedPokemon
        } catch (e: Exception) {
            e.printStackTrace()
            pokemon
        }
    }

    suspend fun insertPokemon(pokemon: PokemonEntity) {
        pokemonDao.insert(pokemon)
    }

    suspend fun updatePokemon(pokemon: PokemonEntity) {
        pokemonDao.update(pokemon)
    }

    suspend fun deletePokemon(pokemon: PokemonEntity) {
        pokemonDao.delete(pokemon)
    }

    fun getAllPokemons(): LiveData<List<PokemonEntity>> = pokemonDao.getAllPokemons()
    fun getFavoritePokemons(): LiveData<List<PokemonEntity>> = pokemonDao.getFavoritePokemons()
    fun searchPokemons(texto: String): LiveData<List<PokemonEntity>> = pokemonDao.searchPokemons(texto)
    fun searchFavoritePokemons(texto: String): LiveData<List<PokemonEntity>> = pokemonDao.searchFavoritePokemons(texto)
}