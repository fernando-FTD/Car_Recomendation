package com.example.carrecomendations.ui.profile;

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.carrecomendations.R
import com.example.carrecomendations.auth.WelcomeActivity
import com.example.carrecomendations.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel.user.observe(viewLifecycleOwner) { firebaseUser ->
            if (firebaseUser != null) {
                binding.profileEmail.text = firebaseUser.email
                if (!firebaseUser.displayName.isNullOrEmpty()) {
                    binding.profileName.text = firebaseUser.displayName
                } else {
                    // Fallback jika displayName kosong
                    binding.profileName.text = "Pengguna"
                }
            } else {
                navigateToWelcome()
            }
        }

        binding.btnEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }

        binding.btnLogout.setOnClickListener {
            profileViewModel.logout()
            navigateToWelcome()
        }
    }

    private fun navigateToWelcome() {
        if (activity != null) {
            val intent = Intent(requireActivity(), WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
