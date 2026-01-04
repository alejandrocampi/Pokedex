package com.acampif.pokedex.repository

import com.acampif.pokedex.R
import com.acampif.pokedex.model.Pokemon

class PokemonRepository {
    private val listaPokemon = mutableListOf<Pokemon>()

    init{
        listaPokemon.add(Pokemon("Bulbasaur", "Nº 001",  R.drawable.bulbasaur,
            "Tras nacer, crece alimentándose durante un tiempo de los nutrientes que contiene el bulbo de su lomo.",
            listOf("PLANTA","VENENO")))
        listaPokemon.add(Pokemon("Ivysaur", "Nº 002", R.drawable.ivysaur,
            "Cuanta más luz solar recibe, más aumenta su fuerza y más se desarrolla el capullo que tiene en el lomo",
            listOf("PLANTA","VENENO")))
        listaPokemon.add(Pokemon("Venusaur", "Nº 003",R.drawable.venusaur,
            "Puede convertir la luz del sol en energía. Por esa razón, es más poderoso en verano.",
            listOf("PLANTA","VENENO")))
        listaPokemon.add(Pokemon("Charmander", "Nº 004",R.drawable.charmander,
            "La llama de su cola indica su fuerza vital. Si está débil, la llama arderá más tenue.",
            listOf("FUEGO")))
        listaPokemon.add(Pokemon("Charmelon", "Nº 005",R.drawable.charmeleon,
            "Al agitar su ardiente cola, eleva poco a poco la temperatura a su alrededor para sofocar a sus rivales.",
            listOf("FUEGO")))
        listaPokemon.add(Pokemon("Charizard", "Nº 006",R.drawable.charizard,
            "Cuando se enfurece de verdad, la llama de la punta de su cola se vuelve de color azul claro.",
            listOf("FUEGO","VOLADOR")))
        listaPokemon.add(Pokemon("Squirtle", "Nº 007",R.drawable.squirtle,
            "Tras nacer, se le hincha el lomo y se le forma un caparazón. Escupe poderosa espuma por la boca.",
            listOf("AGUA")))
        listaPokemon.add(Pokemon("Wartortle", "Nº 008",R.drawable.wartortle,
            "Tiene una cola larga y peluda que simboliza la longevidad y lo hace popular entre los mayores.",
            listOf("AGUA")))
        listaPokemon.add(Pokemon("Blastoise", "Nº 009",R.drawable.blastoise,
            "Aumenta de peso deliberadamente para contrarrestar la fuerza de los chorros de agua que dispara.",
            listOf("AGUA")))
    }
    fun getPokemon(): List<Pokemon>{
        return listaPokemon
    }
}