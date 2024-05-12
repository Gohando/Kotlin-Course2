var heroName: String = ""
fun main() {
    heroName = promptHeroName()
    // changeNarratorMood()
    narrate("$heroName, ${createTitle(heroName)}, heads to the town square")
    visitTavern()
}

private fun promptHeroName(): String {
    narrate("A hero enters the town of Kursk. What is their name?",
        ::makeYellow)
    /*val input = readlnOrNull()
    require(input != null && input.isNotEmpty()) {
    "The hero must have a name."
    }
    return input*/
    println("Madrigal")
    return "Madrigal"
}
private fun makeYellow(message: String) = "\u001b[33;1m$message\u001b[0m"
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