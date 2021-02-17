package com.karpov.lab1.others

import kotlin.math.roundToInt

fun converterLogic(input: String, output: String, from: Double, mainCategory: String): Double {
    var result = 0.0
    when (mainCategory) {
        Constants.SPEED -> {
            when (input) {
                Constants.MS -> {
                    Constants.DUMMY = from * 3.6
                }
                Constants.KMH -> {
                    Constants.DUMMY = from
                }
                Constants.KNOT -> {
                    Constants.DUMMY = from * 1.852
                }
                Constants.FTS -> {
                    Constants.DUMMY = from * 1.097
                }
                Constants.MIH -> {
                    Constants.DUMMY = from * 1.609
                }
            }
            when (output) {
                Constants.KNOT -> {
                    result = ((Constants.DUMMY / 1.852) * 1000.0).roundToInt() / 1000.0
                }
                Constants.FTS -> {
                    result = ((Constants.DUMMY / 1.097) * 1000.0).roundToInt() / 1000.0
                }
                Constants.MIH -> {
                    result = ((Constants.DUMMY / 1.609) * 1000.0).roundToInt() / 1000.0
                }
                Constants.MS -> {
                    result = ((Constants.DUMMY / 3.6) * 1000.0).roundToInt() / 1000.0
                }
                Constants.KMH -> {
                    result = Constants.DUMMY
                }
            }
        }
        Constants.LENGTH -> {
            when (input) {
                Constants.INCH -> {
                    Constants.DUMMY = from / 12
                }
                Constants.FOOT -> {
                    Constants.DUMMY = from
                }
                Constants.YARD -> {
                    Constants.DUMMY = from * 3
                }
                Constants.MILE -> {
                    Constants.DUMMY = from / 1760
                }
                Constants.MILLIMETERS -> {
                    Constants.DUMMY = from * 10e-4
                }
                Constants.CENTIMETERS -> {
                    Constants.DUMMY = from * 10e-3
                }
                Constants.METERS -> {
                    Constants.DUMMY = from
                }
                Constants.KILOMETERS -> {
                    Constants.DUMMY = from * 10e2
                }

            }
            when (output) {
                Constants.MILLIMETERS -> {
                    result = ((Constants.DUMMY * 304) * 1000.0).roundToInt() / 1000.0
                }
                Constants.CENTIMETERS -> {
                    result = ((Constants.DUMMY * 30.48) * 1000.0).roundToInt() / 1000.0
                }
                Constants.METERS -> {
                    result = ((Constants.DUMMY / 3.281) * 1000.0).roundToInt() / 1000.0
                }
                Constants.KILOMETERS -> {
                    result = ((Constants.DUMMY / 3281) * 1000.0).roundToInt() / 1000.0
                }

                Constants.INCH -> {
                    result = ((Constants.DUMMY * 39.37) * 1000.0).roundToInt() / 1000.0
                }
                Constants.FOOT -> {
                    result = ((Constants.DUMMY * 3.281) * 1000.0).roundToInt() / 1000.0
                }
                Constants.YARD -> {
                    result = ((Constants.DUMMY * 1.09361) * 1000.0).roundToInt() / 1000.0
                }
                Constants.MILE -> {
                    result = ((Constants.DUMMY / 1609) * 1000.0).roundToInt() / 1000.0
                }

            }
        }
        Constants.WEIGHT -> {
            when (input) {
                Constants.MILLIGRAM -> {
                    Constants.DUMMY = from / 10e-6
                }
                Constants.GRAM -> {
                    Constants.DUMMY = from / 1000
                }
                Constants.KILOGRAM -> {
                    Constants.DUMMY = from
                }
                Constants.STONE -> {
                    Constants.DUMMY = from * 6.35
                }
                Constants.POUND -> {
                    Constants.DUMMY = from / 2.205
                }
                Constants.OUNCE -> {
                    Constants.DUMMY = from / 35.274
                }

            }
            when (output) {
                Constants.STONE -> {
                    result = ((Constants.DUMMY / 6.35) * 1000.0).roundToInt() / 1000.0
                }
                Constants.POUND -> {
                    result = ((Constants.DUMMY * 2.205) * 1000.0).roundToInt() / 1000.0
                }
                Constants.OUNCE -> {
                    result =
                        ((Constants.DUMMY * 35.274) * 1000.0).roundToInt() / 1000.0
                }
                Constants.MILLIGRAM -> {
                    result = ((Constants.DUMMY * 10e-6) * 1000.0).roundToInt() / 1000.0
                }
                Constants.GRAM -> {
                    result = ((Constants.DUMMY * 1000) * 1000.0).roundToInt() / 1000.0
                }
                Constants.KILOGRAM -> {
                    result = Constants.DUMMY
                }
            }
        }
    }
    return result
}