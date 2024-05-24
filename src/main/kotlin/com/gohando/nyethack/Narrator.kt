package com.gohando.nyethack

import kotlin.random.Random
import kotlin.random.nextInt

var narrationModifier: (String) -> String = { it }

inline fun narrate(
    message: String,
    modifier: (String) -> String = { narrationModifier(it) }
) {
    println(modifier(message))
}

fun changeNarratorMood() {
    val mood: String
    val modifier: (String) -> String

    when (Random.nextInt(1..7)) {
        1 -> {
            mood = "loud"
            modifier = { message ->
                val numExclamationPoints = 3
                message.uppercase() + "!".repeat(numExclamationPoints)
            }
        }
        2 -> {
            mood = "tired"
            modifier = { message ->
                message.lowercase().replace(" ", "... ")
            }
        }
        3 -> {
            mood = "unsure"
            modifier = { message ->
                "$message?"
            }
        }
        4 -> {
            var narrationsGiven = 0
            mood = "like sending an itemized bill"
            modifier = { message ->
                narrationsGiven++
                "$message.\n(I have narrated $narrationsGiven things)"
            }
        }
        5 -> {
            mood = "lazy"
            modifier = { message ->
                val n = message.length
                message.take(n/2)
            }
        }
        6 -> {
            mood = "mysterious"
            modifier = { message ->
                message.uppercase().replace(Regex("[ABCDEGLOSTVZ]")) {
                    when (it.value) {
                        "A" -> "4"
                        "B" -> "8"
                        "C" -> "["
                        "D" -> ")"
                        "E" -> "3"
                        "G" -> "6"
                        "L" -> "1"
                        "O" -> "0"
                        "S" -> "5"
                        "T" -> "7"
                        "V" -> "\\/"
                        "Z" -> "2"
                        else -> it.value
                    }
                }
            }
        }
        7 -> {
            mood = "poetic"
            modifier = { message ->
                message.replace(" ".toRegex()) {
                    when (Random.nextInt(1..4)) {
                        1,2,3 -> it.value
                        else -> "\n"
                    }
                }
            }
        }
        else -> {
            mood = "professional"
            modifier = { message ->
                "$message."
            }
        }
    }
    narrate("The narrator begins to feel $mood")
    narrationModifier = modifier
}