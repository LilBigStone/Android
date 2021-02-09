package com.karpov.lab3.game.host

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.karpov.lab3.others.*
import com.karpov.lab3.others.Constants.DRAW
import com.karpov.lab3.others.Constants.FINISH
import com.karpov.lab3.others.Constants.GAME
import com.karpov.lab3.others.Constants.LOSE
import com.karpov.lab3.others.Constants.O
import com.karpov.lab3.others.Constants.STATS
import com.karpov.lab3.others.Constants.STATUS
import com.karpov.lab3.others.Constants.WIN
import com.karpov.lab3.others.Constants.X

class HostViewModel : ViewModel() {
    var list = MutableLiveData<MutableList<Nucleus>>()
    var step = MutableLiveData<String>()
    var listHost = mutableListOf<Stat>()
    var listClient = mutableListOf<Stat>()
    var clientDisplayName = ""
    var clientEmail = MutableLiveData<String>()
    var gameId = ""
    private val user = Firebase.auth.currentUser!!
    val database = Firebase.database.reference

    private fun writeStats(winner: String, draw: Boolean) {
        var resultHost: String
        var resultClient: String
        if (draw) {
            resultHost = DRAW
            resultClient = DRAW
        } else {
            if (winner == X) {
                resultHost = WIN
                resultClient = LOSE
            } else {
                resultHost = LOSE
                resultClient = WIN

            }
        }
        listHost.add(
            Stat(
                displayNameMy = user.displayName,
                displayNameOpponent = clientDisplayName,
                result = resultHost
            )
        )
        listClient.add(
            Stat(
                displayNameMy = clientDisplayName,
                displayNameOpponent = user.displayName,
                result = resultClient
            )
        )
        database.child(STATS).child(gameId).setValue(listHost)
        database.child(STATS).child(clientEmail.value!!).setValue(listClient)
    }

    fun checkInstance() {
        when (game(list.value!!)) {
            DRAW -> {
                database.child(gameId).child(FINISH).setValue(Finish(Draw = true))
                    .addOnSuccessListener {
                        writeStats(draw = true, winner = "")
                    }

            }
            X -> {
                database.child(gameId).child(FINISH).setValue(Finish(Winner = X, Loser = O))
                    .addOnSuccessListener {
                        writeStats(draw = false, winner = X)
                    }
            }
            O -> {
                database.child(gameId).child(FINISH).setValue(Finish(Winner = O, Loser = X))
                    .addOnSuccessListener {
                        writeStats(draw = false, winner = O)
                    }
            }
        }
    }

    fun deleteGame() {
        database.child(gameId).removeValue()
    }

    fun createGame() {
        database.child(gameId).removeValue()
        database.child(gameId).child(GAME).setValue(makeGameList())
        database.child(gameId).child(Constants.HOST)
            .setValue(Host(email = user.email, displayName = user.displayName))
        database.child(gameId).child(STATUS).setValue(Step())


    }

    fun click(id: Int, gameId: String) {
        for (i in list.value!!) {
            if (i.id == id && i.data.isEmpty() && step.value == X) {
                i.data = X
                database.child(gameId).child(STATUS).setValue(Step(Step = O))
                database.child(gameId).child(GAME).setValue(list.value)
                checkInstance()
            }
        }
    }
}