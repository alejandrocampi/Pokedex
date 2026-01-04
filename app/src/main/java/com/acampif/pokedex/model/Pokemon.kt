package com.acampif.pokedex.model

data class Pokemon (
    val nombre: String,
    val imagen: Int,
    val descripcion: String,
    var favorito: Boolean = false
)