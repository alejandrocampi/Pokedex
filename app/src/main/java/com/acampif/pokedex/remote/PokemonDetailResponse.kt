package com.acampif.pokedex.remote

data class PokemonDetailResponse(
    val types: List<TypeSlot>,
    val species: SpeciesInfo
)

data class TypeSlot(val type: TypeInfo)
data class TypeInfo(val name: String)
data class SpeciesInfo(val url: String)