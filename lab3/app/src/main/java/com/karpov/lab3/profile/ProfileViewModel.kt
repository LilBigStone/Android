package com.karpov.lab3.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.karpov.lab3.others.Constants.EMPTY

class ProfileViewModel : ViewModel() {

    private val currentUser = Firebase.auth.currentUser
    fun updateCurrentUser(url: String, displayNames: String) {
        if (url == EMPTY)
        {
            val profileUpdates = userProfileChangeRequest {
                displayName = displayNames
            }
            currentUser!!.updateProfile(profileUpdates)
        }
        else
        {
            val profileUpdates = userProfileChangeRequest {
                displayName = displayNames
                photoUri = Uri.parse(url)
            }
            currentUser!!.updateProfile(profileUpdates)
        }
    }

}