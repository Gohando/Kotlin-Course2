package com.gohando.nyethack

import kotlin.random.Random

interface Fightable {
    val name: String
    val healthPoints: Int
    val diceCount: Int
    val diceSides: Int

    fun takeDamage(damage: Int)

    fun attack(opponent: Fightable) {
        val damageRoll = (0 until diceCount).sumOf {
            Random.nextInt(diceSides + 1)
        }
        narrate("$name inflicts $damageRoll to ${opponent.name}")
        opponent.takeDamage(damageRoll)
    }
}

abstract class Monster(
    override val name: String,
    val description: String,
    override var healthPoints: Int
) : Fightable {
    override fun takeDamage(damage: Int) {
        healthPoints -= damage
    }
}

class Draugr(
    name: String = "Draugr",
    description: String = "A heavy draugr",
    healthPoints: Int = 50
) : Monster(name, description, healthPoints) {
    override val diceCount = 4
    override val diceSides = 3
}

class Werewolf(
    name: String = "Werewolf",
    description: String = "A wild werewolf",
    healthPoints: Int = 20
) : Monster(name, description, healthPoints) {
    override val diceCount = 2
    override val diceSides = 10
}

class Dragon(
    name: String = "Dragon",
    description: String = "A horrifying dragon",
    healthPoints: Int = 80
) : Monster(name, description, healthPoints) {
    override val diceCount = 4
    override val diceSides = 12
}

class Goblin(
    name: String = "Goblin",
    description: String = "A nastya-looking goblin",
    healthPoints: Int = 30
) : Monster(name, description, healthPoints) {
    override val diceCount = 2
    override val diceSides = 8
}