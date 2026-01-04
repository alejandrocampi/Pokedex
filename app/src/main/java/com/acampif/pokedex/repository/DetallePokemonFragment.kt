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
import com.acampif.pokedex.databinding.FragmentPokedexBinding
import com.acampif.pokedex.viewmodel.PokemonViewModel
import com.google.android.material.chip.Chip


class DetallePokemonFragment : Fragment() {

    private lateinit var binding: FragmentDetallePokemonBinding
    private lateinit var viewModel: PokemonViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetallePokemonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(PokemonViewModel::class.java)

        viewModel.pokemonSeleccionado.observe(viewLifecycleOwner){ pokemon ->
            if(pokemon != null){
                binding.tvNumeroDetalle.text = pokemon.numero
                binding.tvNombreDetalle.text = pokemon.nombre.uppercase()
                binding.ivPokemonDetalle.setImageResource(pokemon.imagen)
                binding.tvDescripcionDetalle.text = pokemon.descripcion

                mostrarTipos(pokemon.tipos)
            }
        }
    }

    private fun mostrarTipos(tipos: List<String>){
        binding.cgTipos.removeAllViews()

        for(tipo in tipos){
            val chip = Chip(requireContext()).apply{
                text = tipo
                isClickable = false
                chipBackgroundColor = obtenerColorTipo(tipo)
                setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
            }
            binding.cgTipos.addView(chip)
        }
    }

    private fun obtenerColorTipo(tipo: String): android.content.res.ColorStateList {
        val colorRes = when(tipo.uppercase()){
            "PLANTA" -> R.color.tipo_planta
            "FUEGO" -> R.color.tipo_fuego
            "AGUA" -> R.color.tipo_agua
            "VENENO" -> R.color.tipo_veneno
            "VOLADOR" -> R.color.tipo_volador
            else -> R.color.tipo_default
        }
        return android.content.res.ColorStateList.valueOf(
            ContextCompat.getColor(requireContext(), colorRes)
        )
        }

}