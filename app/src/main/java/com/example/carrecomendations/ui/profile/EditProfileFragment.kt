package com.example.carrecomendations.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.carrecomendations.R
import com.example.carrecomendations.databinding.FragmentEditProfileBinding
import com.google.android.gms.tasks.Tasks

class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ambil data user saat ini dan tampilkan
        profileViewModel.user.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding.editName.setText(it.displayName ?: it.email?.split('@')?.get(0))
                binding.editEmail.setText(it.email)
            }
        }

        // Logika Tombol Simpan
        binding.btnSaveProfile.setOnClickListener {
            saveProfileChanges()
        }
    }

    private fun saveProfileChanges() {
        val newName = binding.editName.text.toString().trim()
        val newPassword = binding.editPassword.text.toString().trim()
        val confirmPassword = binding.editConfirmPassword.text.toString().trim()

        if (newName.isEmpty()) {
            Toast.makeText(context, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        }

        val updateTasks = mutableListOf<com.google.android.gms.tasks.Task<*>>()

        // 1. Cek dan siapkan update nama
        profileViewModel.user.value?.let {
            if (newName != it.displayName) {
                profileViewModel.updateProfile(newName)?.let { task -> updateTasks.add(task) }
            }
        }

        // 2. Cek dan siapkan update password
        if (newPassword.isNotEmpty()) {
            if (newPassword.length < 6) {
                Toast.makeText(context, "Password minimal 6 karakter", Toast.LENGTH_SHORT).show()
                return
            }
            if (newPassword != confirmPassword) {
                Toast.makeText(context, "Password baru dan konfirmasi tidak cocok", Toast.LENGTH_SHORT).show()
                return
            }
            profileViewModel.updatePassword(newPassword)?.let { task -> updateTasks.add(task) }
        }

        // 3. Jalankan semua update
        if (updateTasks.isEmpty()) {
            Toast.makeText(context, "Tidak ada perubahan untuk disimpan", Toast.LENGTH_SHORT).show()
            return
        }

        Tasks.whenAllComplete(updateTasks)
            .addOnCompleteListener { taskResult ->
                if (taskResult.isSuccessful && taskResult.result.all { it.isSuccessful }) {
                    // Jika semua berhasil, navigasi ke halaman sukses
                    findNavController().navigate(R.id.action_editProfileFragment_to_successFragment)
                } else {
                    // Cari tahu error spesifiknya
                    val firstError = taskResult.result.firstOrNull { !it.isSuccessful }
                    val errorMessage = firstError?.exception?.message ?: "Gagal memperbarui profil"
                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                }
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
