package com.acampif.pokedex.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemon_table ORDER BY id ASC")
    fun getAllPokemons(): LiveData<List<PokemonEntity>>

    @Query("SELECT * FROM pokemon_table WHERE isFavorite = 1 ORDER BY id ASC")
    fun getFavoritePokemons(): LiveData<List<PokemonEntity>>

    @Query("SELECT * FROM pokemon_table WHERE name LIKE :texto || '%' ORDER BY id ASC")
    fun searchPokemons(texto: String): LiveData<List<PokemonEntity>>

    @Query("SELECT * FROM pokemon_table WHERE isFavorite = 1 AND name LIKE :texto || '%' ORDER BY id ASC")
    fun searchFavoritePokemons(texto: String): LiveData<List<PokemonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemons: List<PokemonEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemon: PokemonEntity)

    @Update
    suspend fun update(pokemon: PokemonEntity)

    @Delete
    suspend fun delete(pokemon: PokemonEntity)
}