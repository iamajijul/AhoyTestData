package com.ajijul.network.utils

import kotlin.math.roundToInt

fun Double.toCelsius():String = (this - 273.15).roundToInt().toString() + 0x00B0.toChar()