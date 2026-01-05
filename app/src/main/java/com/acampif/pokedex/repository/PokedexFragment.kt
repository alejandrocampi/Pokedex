package com.acampif.pokedex.repository

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.acampif.pokedex.databinding.FragmentPokedexBinding
import com.acampif.pokedex.viewmodel.PokemonViewModel

class PokedexFragment : Fragment() {

    private lateinit var binding: FragmentPokedexBinding
    private lateinit var viewModel: PokemonViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokedexBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(PokemonViewModel::class.java)

        val adapter = PokemonAdapter(emptyList(), viewModel)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = androidx.recyclerview.widget.GridLayoutManager(requireContext(), 2)

        viewModel.pokemon.observe(viewLifecycleOwner) { lista ->
            adapter.actualizarLista(lista)
        }

        val swipeHandler = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val posicion = viewHolder.bindingAdapterPosition
                viewModel.eliminarPokemon(posicion)
            }
        }
        ItemTouchHelper(swipeHandler).attachToRecyclerView(binding.recyclerView)

    }
}