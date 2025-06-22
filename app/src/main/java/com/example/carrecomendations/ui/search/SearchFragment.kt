package com.example.carrecomendations.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.carrecomendations.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinner()
        setupSearchView()
        setupSearchButton()
    }

    private fun setupSpinner() {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, searchViewModel.carTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCarType.adapter = adapter
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                performSearch()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun setupSearchButton() {
        binding.btnSearch.setOnClickListener {
            performSearch()
        }
    }

    private fun performSearch() {
        val query = binding.searchView.query.toString().trim()
        val selectedType = binding.spinnerCarType.selectedItem.toString()

        val minPriceText = binding.etMinPrice.text.toString().trim()
        val maxPriceText = binding.etMaxPrice.text.toString().trim()

        val minPrice = if (minPriceText.isNotEmpty()) {
            minPriceText.toIntOrNull() ?: 0
        } else {
            0
        }

        val maxPrice = if (maxPriceText.isNotEmpty()) {
            maxPriceText.toIntOrNull() ?: 0
        } else {
            0
        }

        // Debug log
        println("Search params - Query: '$query', Type: '$selectedType', MinPrice: $minPrice, MaxPrice: $maxPrice")

        // Perform search
        val results = searchViewModel.performSearch(query, selectedType, minPrice, maxPrice)

        // Debug log
        println("Search results count: ${results.size}")

        // Navigate ke results page
        try {
            val resultsArray = results.toTypedArray()
            val action = SearchFragmentDirections.actionSearchFragmentToSearchResultsFragment(
                searchResults = resultsArray,
                searchQuery = query,
                searchType = selectedType,
                minPrice = minPrice,
                maxPrice = maxPrice
            )
            findNavController().navigate(action)
        } catch (e: Exception) {
            Toast.makeText(context, "Error navigating to results: ${e.message}", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
