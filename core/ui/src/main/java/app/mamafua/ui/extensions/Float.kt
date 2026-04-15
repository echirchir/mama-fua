package app.mamafua.ui.extensions

import kotlin.math.pow
import kotlin.math.roundToInt

fun Float.round(decimals: Int): Float {
    val factor = 10.0.pow(decimals)
    return (this * factor).roundToInt() / factor.toFloat()
}
