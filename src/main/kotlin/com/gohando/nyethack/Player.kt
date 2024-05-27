package com.gohando.nyethack

class Player (
    initialName: String,
    val hometown: String = "Jousvillage",
    override var healthPoints: Int,
    val isImmoral: Boolean
) : Fightable {
    override var name = initialName
        get() = field.replaceFirstChar { it.uppercase() }
        private set(value) {
            field = value.trim()
        }
    override val diceCount = 3
    override val diceSides = 4

    val title: String
        get() = when {
        name.all { it.isDigit() } -> "The Id"
        name.none { it.isLetter() } -> "The Password"
        name.lowercase() == name.lowercase().reversed() -> "The Palindrome"
        name == name.uppercase() -> "The Shouter"
        name.count() > 20 -> "The longie"
        name.count { it.lowercase() in "aeiou" } > 4 -> "The Master of Vowel"
        else -> "The Renowned Hero"
        }
    private val prophecy by lazy {
        narrate("$name embarks on an arduous quest to locate a fortune teller")
        Thread.sleep(3000)
        narrate("The fortune teller bestows a prophecy upon $name")
        "An intrepid hero from $hometown shall some day " + listOf(
            "form an unlikely bond between two warring factions",
            "take possession of an otherworldly blade",
            "bring the gift of creation back to the world",
            "best the world-eater"
        ).random()
    }
    init {
        require(healthPoints > 0) { "healthPoints must be greater than zero" }
        require(name.isNotBlank()) { "Player must have a name" }
    }
    constructor(name: String) : this(
        initialName = name,
        healthPoints = 100,
        isImmoral = false
    ) {
        if (name.equals("Jous", ignoreCase = true)) {
            healthPoints = 500
        }
    }
    fun changeName(newName: String) {
        narrate("$name changes their name to $newName")
        name = newName
    }

    fun prophesize() {
        narrate("$name thinks about their future")
        narrate("A fortune teller told Mardigal, \"$prophecy\"")
    }

    override fun takeDamage(damage: Int) {
        if (!isImmoral) {
            healthPoints -= damage
        }
    }

    fun castFireball(numFireballs: Int = 2) {
        narrate("A grass with fireballs sprints into existence (x$numFireballs)")
    }
}