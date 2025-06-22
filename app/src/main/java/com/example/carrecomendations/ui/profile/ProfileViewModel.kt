package com.example.carrecomendations.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

class ProfileViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _user = MutableLiveData<FirebaseUser?>()
    val user: LiveData<FirebaseUser?> = _user

    init {
        _user.value = auth.currentUser
    }

    fun updateProfile(newName: String): Task<Void>? {
        val user = auth.currentUser
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(newName)
            .build()
        return user?.updateProfile(profileUpdates)
    }

    fun updatePassword(newPassword: String): Task<Void>? {
        return auth.currentUser?.updatePassword(newPassword)
    }

    fun logout() {
        auth.signOut()
    }
}
