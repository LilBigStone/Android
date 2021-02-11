package com.karpov.lab3.game.client

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.karpov.lab3.others.*
import com.karpov.lab3.others.Constants.GAME
import com.karpov.lab3.others.Constants.O
import com.karpov.lab3.others.Constants.STATUS
import com.karpov.lab3.others.Constants.X

class ClientViewModel : ViewModel() {
    var list = MutableLiveData<MutableList<Nucleus>>()
    var step = MutableLiveData<String>()
    var gameId = ""
    var listClient = mutableListOf<Stat>()
    var hostDisplayName = MutableLiveData<String>()
    val database = Firebase.database.reference
    var auth = Firebase.auth


//    private fun writeStats(winner: String, draw: Boolean) {
//        var resultClient: String
//        if (draw) {
//            resultClient = Constants.DRAW
//        } else {
//            if (winner == X) {
//                resultClient = Constants.LOSE
//            } else {
//                resultClient = Constants.WIN
//
//            }
//        }
//        listClient.add(
//            Stat(
//                displayNameMy = auth.currentUser!!.displayName,
//                displayNameOpponent = hostDisplayName.value,
//                result = resultClient
//            )
//        )
//        database.child(Constants.STATS).child(auth.currentUser!!.email.toString().replace(".", ""))
//            .setValue(listClient)
//    }
//
//    fun checkInstance() {
//        when (game(list.value!!)) {
//            Constants.DRAW -> {
//                database.child(gameId).child(Constants.FINISH).setValue(Finish(Draw = true))
//                    .addOnSuccessListener {
//                        writeStats(draw = true, winner = "")
//                    }
//
//            }
//            X -> {
//                database.child(gameId).child(Constants.FINISH)
//                    .setValue(Finish(Winner = X, Loser = O))
//                    .addOnSuccessListener {
//                        writeStats(draw = false, winner = X)
//                    }
//            }
//            O -> {
//                database.child(gameId).child(Constants.FINISH)
//                    .setValue(Finish(Winner = O, Loser = X))
//                    .addOnSuccessListener {
//                        writeStats(draw = false, winner = O)
//                    }
//            }
//        }
//    }

    fun click(id: Int, gameId: String) {
        for (i in list.value!!) {
            if (i.id == id && i.data.isEmpty() && step.value == O) {
                i.data = O
                val database = Firebase.database.reference
                database.child(gameId).child(STATUS).setValue(Step(Step = X))
                database.child(gameId).child(GAME).setValue(list.value)
            }
        }
    }
}