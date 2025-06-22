package com.example.carrecomendations.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.carrecomendations.databinding.FragmentFavoritesBinding
import com.example.carrecomendations.ui.SharedViewModel

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var favoritesAdapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeFavorites()
    }

    private fun setupRecyclerView() {
        favoritesAdapter = FavoritesAdapter(emptyList()) { selectedCar ->
             val action = FavoritesFragmentDirections.actionNavigationFavoritesToDetailFragment(selectedCar)
             findNavController().navigate(action)
        }
        binding.rvFavorites.adapter = favoritesAdapter
    }

    private fun observeFavorites() {
        sharedViewModel.favoriteCars.observe(viewLifecycleOwner) { favorites ->
            if (favorites.isEmpty()) {
                binding.tvEmptyFavorites.visibility = View.VISIBLE
                binding.rvFavorites.visibility = View.GONE
            } else {
                binding.tvEmptyFavorites.visibility = View.GONE
                binding.rvFavorites.visibility = View.VISIBLE
                favoritesAdapter.updateData(favorites.toList().sortedBy { it.name })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}