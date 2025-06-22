package com.example.carrecomendations.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.carrecomendations.databinding.FragmentSearchResultsBinding
import com.example.carrecomendations.ui.home.CarAdapter

class SearchResultsFragment : Fragment() {

    private var _binding: FragmentSearchResultsBinding? = null
    private val binding get() = _binding!!

    private val args: SearchResultsFragmentArgs by navArgs()
    private lateinit var carAdapter: CarAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchResultsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupRecyclerView()
        displayResults()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupRecyclerView() {
        carAdapter = CarAdapter(emptyList()) { selectedCar ->
            val action = SearchResultsFragmentDirections.actionSearchResultsFragmentToDetailFragment(selectedCar)
            findNavController().navigate(action)
        }
        binding.rvSearchResults.adapter = carAdapter
    }

    private fun displayResults() {
        val results = args.searchResults.toList()
        val searchQuery = args.searchQuery
        val searchType = args.searchType
        val minPrice = args.minPrice
        val maxPrice = args.maxPrice

        println("Displaying results: ${results.size} cars")

        // Update search info text
        val searchInfo = buildSearchInfoText(results.size, searchQuery, searchType, minPrice, maxPrice)
        binding.tvSearchInfo.text = searchInfo

        if (results.isNotEmpty()) {
            binding.rvSearchResults.visibility = View.VISIBLE
            binding.tvNoResults.visibility = View.GONE
            carAdapter = CarAdapter(results) { selectedCar ->
                val action = SearchResultsFragmentDirections.actionSearchResultsFragmentToDetailFragment(selectedCar)
                findNavController().navigate(action)
            }
            binding.rvSearchResults.adapter = carAdapter
        } else {
            binding.rvSearchResults.visibility = View.GONE
            binding.tvNoResults.visibility = View.VISIBLE
        }
    }

    private fun buildSearchInfoText(
        resultCount: Int,
        query: String,
        type: String,
        minPrice: Int,
        maxPrice: Int
    ): String {
        val parts = mutableListOf<String>()

        if (query.isNotEmpty()) {
            parts.add("'$query'")
        }

        if (type != "Semua") {
            parts.add("tipe $type")
        }

        if (minPrice > 0 || maxPrice > 0) {
            when {
                minPrice > 0 && maxPrice > 0 -> parts.add("harga ${minPrice}-${maxPrice} juta")
                minPrice > 0 -> parts.add("harga min ${minPrice} juta")
                maxPrice > 0 -> parts.add("harga max ${maxPrice} juta")
            }
        }

        val searchCriteria = if (parts.isNotEmpty()) {
            " untuk ${parts.joinToString(", ")}"
        } else {
            ""
        }

        return "Menampilkan $resultCount hasil$searchCriteria"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
