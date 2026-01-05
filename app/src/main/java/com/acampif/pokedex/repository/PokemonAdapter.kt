package com.acampif.pokedex.repository

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.acampif.pokedex.R
import com.acampif.pokedex.databinding.ViewholderPokemonBinding
import com.acampif.pokedex.model.Pokemon
import com.acampif.pokedex.viewmodel.PokemonViewModel

class PokemonAdapter(
    private var lista: List<Pokemon>,
    private val viewModel: PokemonViewModel
): RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.viewholder_pokemon, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = lista[position]

        holder.binding.tvNumero.text = pokemon.numero
        holder.binding.tvNombre.text = pokemon.nombre
        holder.binding.ivPokemon.setImageResource(pokemon.imagen)

        holder.itemView.setOnClickListener { view ->
            viewModel.seleccionarPokemon(pokemon)
            Navigation.findNavController(view)
                .navigate(R.id.action_pokedexFragment_to_detallePokemonFragment)
        }
    }

    override fun getItemCount(): Int = lista.size

    fun actualizarLista(nuevaLista: List<Pokemon>){
        lista = nuevaLista
        notifyDataSetChanged()
    }

    class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ViewholderPokemonBinding.bind(view)
    }

}