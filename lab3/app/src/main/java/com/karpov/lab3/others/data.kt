package com.karpov.lab3.others

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Host(
    var displayName: String? = "",
    var email: String? = "",
)

@IgnoreExtraProperties
data class Nucleus(
    var id: Int = 0,
    var data: String = "",
)

@IgnoreExtraProperties
data class Stat(
    var displayNameMy: String? = "",
    var displayNameOpponent: String? = "",
    var result : String? = ""
)

@IgnoreExtraProperties
data class Client(
    var displayName: String? = "",
    var email: String? = "",
)

@IgnoreExtraProperties
data class Step(
    var Step: String? = "X",
)

@IgnoreExtraProperties
data class Finish(
    var Winner: String? = "",
    var Loser: String? = "",
    var Draw: Boolean = false,
)

fun makeGameList(): MutableList<Nucleus> {
    val list = mutableListOf<Nucleus>()
    for (i in 0..8) {
        list.add(Nucleus(id = i))
    }
    return list
}