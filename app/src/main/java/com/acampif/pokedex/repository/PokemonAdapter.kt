package com.acampif.pokedex.repository

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.acampif.pokedex.R
import com.acampif.pokedex.databinding.ViewholderPokemonBinding
import com.acampif.pokedex.local.PokemonEntity
import com.acampif.pokedex.PokemonViewModel
import com.bumptech.glide.Glide

class PokemonAdapter(
    private val viewModel: PokemonViewModel
): RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    private var lista = emptyList<PokemonEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.viewholder_pokemon, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = lista[position]

        holder.binding.tvNumero.text = "#${String.format("%03d", pokemon.id)}"
        holder.binding.tvNombre.text = pokemon.name.replaceFirstChar { it.uppercase() }

        Glide.with(holder.itemView.context)
            .load(pokemon.getImageUrl())
            .into(holder.binding.ivPokemon)

        if(pokemon.isFavorite){
            holder.binding.iconFavorite.setImageResource(android.R.drawable.btn_star_big_on)
        } else {
            holder.binding.iconFavorite.setImageResource(android.R.drawable.btn_star_big_off)
        }

        holder.binding.iconFavorite.setOnClickListener {
            viewModel.toggleFavorite(pokemon)
        }

        holder.itemView.setOnClickListener { view ->
            viewModel.seleccionarPokemon(pokemon)
            Navigation.findNavController(view)
                .navigate(R.id.action_global_detallePokemonFragment)
        }
    }

    override fun getItemCount(): Int = lista.size

    fun actualizarLista(nuevaLista: List<PokemonEntity>){
        lista = nuevaLista
        notifyDataSetChanged()
    }

    fun getPokemonAt(position: Int): PokemonEntity {
        return lista[position]
    }

    class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ViewholderPokemonBinding.bind(view)
    }
}