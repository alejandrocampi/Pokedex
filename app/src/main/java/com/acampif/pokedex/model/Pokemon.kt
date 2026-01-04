package com.acampif.pokedex.model

data class Pokemon (
    val nombre: String,
    val numero: String,
    val imagen: Int,
    val descripcion: String,
    val tipos: List<String>,
    var favorito: Boolean = false
)