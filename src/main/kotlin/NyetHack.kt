fun main() {
    narrate("A hero enters the town of Kursk. What is their name?")
    { message ->
        "\u001b[33;1m$message\u001b[0m"
    }
    val heroName = readlnOrNull()
    require(!heroName.isNullOrEmpty()) {
        "The hero must have a name."
    }

    changeNarratorMood()
    narrate("$heroName, ${createTitle(heroName)}, heads to the town square")
}

private fun createTitle(name: String): String {
    return when {
        name.all { it.isDigit() } -> "The Id"
        name.none { it.isLetter() } -> "The Password"
        name.lowercase() == name.lowercase().reversed() -> "The palindrome"
        name == name.uppercase() -> "The remarkable"
        name.count() > 20 -> "The longie"
        name.count { it.lowercase() in "aeiou" } > 4 -> "The Master of Vowel"

        else -> "The Renowned Hero"
    }
}