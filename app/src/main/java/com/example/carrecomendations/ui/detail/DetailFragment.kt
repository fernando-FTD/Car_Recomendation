package com.example.carrecomendations.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.carrecomendations.R
import com.example.carrecomendations.databinding.FragmentDetailBinding
import com.example.carrecomendations.ui.SharedViewModel
import com.example.carrecomendations.ui.home.Car

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var currentCar: Car

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentCar = args.carData

        setupToolbar()
        displayCarData()
        observeFavoriteStatus()
        setupClickListeners()
    }

    private fun setupToolbar() {
        binding.toolbar.title = currentCar.category
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun displayCarData() {
        binding.ivCarImage.setImageResource(currentCar.imageResId)
        binding.tvCarName.text = currentCar.name
        binding.tvCarPrice.text = currentCar.price
        binding.tvCarDescription.text = currentCar.description
    }

    private fun observeFavoriteStatus() {
        sharedViewModel.favoriteCars.observe(viewLifecycleOwner) { favorites ->
            val isFavorite = favorites.any { it.id == currentCar.id }
            updateFavoriteIcon(isFavorite)
        }
    }

    private fun updateFavoriteIcon(isFavorite: Boolean) {
        val context = requireContext()
        if (isFavorite) {
            binding.ivFavoriteIcon.setImageResource(R.drawable.ic_favorite)
            binding.ivFavoriteIcon.setColorFilter(ContextCompat.getColor(context, R.color.red))
        } else {
            binding.ivFavoriteIcon.setImageResource(R.drawable.ic_favorite_border)
            binding.ivFavoriteIcon.setColorFilter(ContextCompat.getColor(context, R.color.dark_gray))
        }
    }

    private fun setupClickListeners() {
        val favoriteClickListener = View.OnClickListener {
            sharedViewModel.toggleFavorite(currentCar)
        }
        binding.ivFavoriteIcon.setOnClickListener(favoriteClickListener)
        binding.btnAddToFavorite.setOnClickListener(favoriteClickListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
