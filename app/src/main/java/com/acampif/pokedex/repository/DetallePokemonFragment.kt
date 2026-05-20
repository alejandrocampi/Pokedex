package com.acampif.pokedex.repository

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.acampif.pokedex.R
import com.acampif.pokedex.databinding.FragmentDetallePokemonBinding
import com.acampif.pokedex.PokemonViewModel
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip

class DetallePokemonFragment : Fragment() {

    private lateinit var binding: FragmentDetallePokemonBinding
    private lateinit var viewModel: PokemonViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDetallePokemonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[PokemonViewModel::class.java]

        viewModel.pokemonSeleccionado.observe(viewLifecycleOwner) { pokemon ->
            binding.tvNumeroDetalle.text = "#${String.format("%03d", pokemon.id)}"
            binding.tvNombreDetalle.text = pokemon.name.replaceFirstChar { it.uppercase() }
            binding.tvDescripcionDetalle.text = pokemon.description

            Glide.with(requireContext()).load(pokemon.getImageUrl()).into(binding.ivPokemonDetalle)

            val listaTipos = pokemon.types.split(",").filter { it.isNotEmpty() }
            mostrarTipos(listaTipos)
        }
    }

    private fun mostrarTipos(tipos: List<String>){
        binding.cgTipos.removeAllViews()
        for(tipo in tipos){
            val chip = Chip(requireContext()).apply{
                text = tipo.replaceFirstChar { it.uppercase() }
                isClickable = false
                chipBackgroundColor = obtenerColorTipo(tipo)
                setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
            }
            binding.cgTipos.addView(chip)
        }
    }

    private fun obtenerColorTipo(tipo: String): android.content.res.ColorStateList {
        val colorRes = when(tipo.lowercase()){
            "grass" -> R.color.tipo_planta
            "fire" -> R.color.tipo_fuego
            "water" -> R.color.tipo_agua
            "poison" -> R.color.tipo_veneno
            "flying" -> R.color.tipo_volador
            "ice" -> R.color.tipo_hielo
            "bug" -> R.color.tipo_bicho
            "normal" -> R.color.tipo_normal
            "electric" -> R.color.tipo_electrico
            "ground" -> R.color.tipo_tierra
            "fairy" -> R.color.tipo_hada
            "fighting" -> R.color.tipo_lucha
            "psychic" -> R.color.tipo_psiquico
            "rock" -> R.color.tipo_roca
            "ghost" -> R.color.tipo_fantasma
            "dragon" -> R.color.tipo_dragon
            "dark" -> R.color.tipo_siniestro
            "steel" -> R.color.tipo_acero
            else -> R.color.tipo_default
        }
        return android.content.res.ColorStateList.valueOf(ContextCompat.getColor(requireContext(), colorRes))
    }
}