package com.acampif.pokedex.repository

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.acampif.pokedex.R
import com.acampif.pokedex.databinding.FragmentPokedexBinding
import com.acampif.pokedex.PokemonViewModel

class PokedexFragment : Fragment() {

    private lateinit var binding: FragmentPokedexBinding
    private lateinit var viewModel: PokemonViewModel
    private lateinit var adapter: PokemonAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokedexBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(PokemonViewModel::class.java)

        adapter = PokemonAdapter(viewModel)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        viewModel.pokemons.observe(viewLifecycleOwner) { lista ->
            adapter.actualizarLista(lista)
            if (lista.isEmpty()) {
                binding.tvNoResultados.visibility = View.VISIBLE
            } else {
                binding.tvNoResultados.visibility = View.GONE
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { mensaje ->
            if (mensaje != null) {
                Toast.makeText(requireContext(), mensaje, Toast.LENGTH_LONG).show()
                viewModel.clearError()
            }
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.loadMorePokemons()
                }
            }
        })

        val swipeHandler = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val posicion = viewHolder.bindingAdapterPosition
                val pokemon = adapter.getPokemonAt(posicion)
                viewModel.deletePokemon(pokemon)
            }
        }
        ItemTouchHelper(swipeHandler).attachToRecyclerView(binding.recyclerView)

        val menuHost = requireActivity() as MenuHost
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_search, menu)

                val searchItem = menu.findItem(R.id.action_search)
                val searchView = searchItem.actionView as SearchView

                searchView.queryHint = "Buscar Pokémon"
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        viewModel.buscarPokemon(query ?: "")
                        return true
                    }
                    override fun onQueryTextChange(newText: String?): Boolean {
                        viewModel.buscarPokemon(newText ?: "")
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean = false
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.fabAgregarPokemon.setOnClickListener {
            mostrarDialogoAgregar()
        }

        viewModel.loadMorePokemons()
    }

    private fun mostrarDialogoAgregar() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_agregar_pokemon, null)

        AlertDialog.Builder(requireContext())
            .setTitle("Nuevo Pokémon")
            .setView(dialogView)
            .setPositiveButton("Guardar") { _, _ ->
                val idStr = dialogView.findViewById<EditText>(R.id.etIdManual).text.toString()
                val nombre = dialogView.findViewById<EditText>(R.id.etNombreManual).text.toString()
                val tipo = dialogView.findViewById<EditText>(R.id.etTipoManual).text.toString().lowercase()
                val desc = dialogView.findViewById<EditText>(R.id.etDescripcionManual).text.toString()

                if (idStr.isNotEmpty() && nombre.isNotEmpty()) {
                    viewModel.agregarPokemon(idStr.toInt(), nombre, tipo, desc)
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}