package com.karpov.lab3.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.karpov.lab3.others.*
import com.karpov.lab3.others.Constants.CLIENT
import com.karpov.lab3.others.Constants.GAME
import com.karpov.lab3.others.Constants.HOST
import com.karpov.lab3.others.Constants.STATUS

class MainViewModel : ViewModel() {

    private lateinit var database: DatabaseReference
    private val user = Firebase.auth.currentUser!!
    private val gameId = user.email?.replace(".", "")!!
    var navigate = MutableLiveData<Boolean>()
    var navigateToHost = MutableLiveData<Boolean>()
    var list = MutableLiveData<List<Stat>>()

    fun doneNavigate() {
        navigate.value = false
        navigateToHost.value = false
    }

    init {
        navigate.value = false
        navigateToHost.value = false
    }

    fun clientConnect(id: String, displayName: String, email: String) {
        database = Firebase.database.reference
        database.child(id).child(CLIENT)
            .setValue(Client(displayName = displayName, email = email))
            .addOnSuccessListener {
                navigateToHost.value = true
            }
    }

    fun createGame() {
        database = Firebase.database.reference
        database.child(gameId).child(HOST)
            .setValue(Host(email = user.email, displayName = user.displayName))
        database.child(gameId).child(STATUS).setValue(Step())
        database.child(gameId).child(GAME).setValue(makeGameList())
            .addOnSuccessListener {
                navigate.value = true
            }
            .addOnFailureListener {
                Log.i("CreateGame", it.toString())
            }
    }
}