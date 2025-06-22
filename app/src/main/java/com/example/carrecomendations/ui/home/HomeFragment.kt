package com.example.carrecomendations.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.carrecomendations.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe data sapaan dari ViewModel dan update TextView
        homeViewModel.greetingText.observe(viewLifecycleOwner) { greeting ->
            binding.textGreeting.text = greeting
        }

        // Observe data mobil dari ViewModel dan update RecyclerView
        homeViewModel.cars.observe(viewLifecycleOwner) { carList ->
            val adapter = CarAdapter(carList) { selectedCar ->
                val action = HomeFragmentDirections.actionNavigationHomeToDetailFragment(selectedCar)
                findNavController().navigate(action)
            }
            binding.rvCars.adapter = adapter
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh data setiap kali fragment muncul
        homeViewModel.refreshData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
