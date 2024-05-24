package com.gohando.nyethack

lateinit var player: Player
fun main() {
    narrate("Welcome to NyetHack!")
    val playerName = promptHeroName()
    player = Player(playerName)
    //changeNarratorMood()
    player.prophesize()

    val currentRoom: Room = Tavern()
    narrate("${player.name} of ${player.hometown}, ${player.title} is in ${currentRoom.description()}")
    narrate("${player.name}, ${if (player.isImmoral) "an immortal" else "a mortal"}," +
            " has ${player.healthPoints} health points")
    currentRoom.enterRoom()
    player.castFireball(5)
    player.prophesize()
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