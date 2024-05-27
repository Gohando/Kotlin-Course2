package com.gohando.nyethack

import kotlin.system.exitProcess

lateinit var player: Player
fun main() {
    narrate("Welcome to NyetHack!")
    val playerName = promptHeroName()
    player = Player(playerName)
    //changeNarratorMood()

    Game.play()
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
object Game {
    private val worldMap = listOf(
        listOf(TownSquare(), Tavern(), Room("Back Room")),
        listOf(MonsterRoom("A Long Corridor"), MonsterRoom("A Generic Room")),
        listOf(MonsterRoom("The Dungeon")),
        listOf(MonsterRoom("The secret cave"))
    )
    private var currentRoom: Room = worldMap[0][0]
    private var currentPosition = Coordinate(0, 0)

    init {
        narrate("Welcome, adventurer")
        narrate(
            "${player.name}, ${if (player.isImmoral) "an immortal" else "a mortal"}," +
                    " has ${player.healthPoints} health points"
        )
    }

    fun play() {
        while (true) {
            narrate("${player.name} of ${player.hometown}, ${player.title} is in ${currentRoom.getName()} ${currentRoom.getRoomStatus()}")
            currentRoom.enterRoom()

            print("Enter your command: ")
            GameInput(readln()).processCommand()
        }
    }

    fun move(direction: Direction) {
        val newPosition = direction.updateCoordinate(currentPosition)
        val newRoom = worldMap.getOrNull(newPosition.y)?.getOrNull(newPosition.x)

        if (newRoom != null) {
            narrate("The hero moves ${direction.name}")
            currentPosition = newPosition
            currentRoom = newRoom
        } else {
            narrate("You can't move ${direction.name}")
        }
    }

    fun fight() {
        val monsterRoom = currentRoom as? MonsterRoom
        val currentMonster = monsterRoom?.monster
        if (currentMonster == null) {
            narrate("There's nothing to fight here")
            return
        }
        while (player.healthPoints > 0 && currentMonster.healthPoints > 0) {
            player.attack(currentMonster)
            if (currentMonster.healthPoints > 0) {
                currentMonster.attack(player)
            }
            Thread.sleep(1000)
        }
        if (player.healthPoints <= 0) {
            narrate("You have been defeated! Thanks for playing :)")
            exitProcess(0)
        } else {
            narrate("${currentMonster.name} has been defeated...")
            monsterRoom.monster = null
            monsterRoom.status = "Calm"
            narrate("${player.name} has ${player.healthPoints} HP left")
        }
    }

    fun map() {
        worldMap.forEach { row ->
            row.forEach { room ->
                print(if (room == currentRoom) "X" else "O")
                print(" ")
            }
            print("\n")
        }
    }

    private class GameInput(arg: String?) {
        private val input = arg ?: ""
        val command = input.split(" ")[0]
        val argument = input.split(" ").getOrElse(1) { "" }
        fun processCommand() = when (command.lowercase()) {
            "move" -> {
                val direction = Direction.entries.firstOrNull() {
                    it.name.equals(argument, ignoreCase = true)
                }
                if (direction != null) {
                    move(direction)
                } else {
                    narrate("This direction is not valid")
                }
            }

            "fight" -> fight()
            "cast" -> {
                if (argument == "fireball") player.castFireball()
                else narrate("Can't cast that")
            }

            "prophesize" -> player.prophesize()
            "map" -> map()
            "ring" -> {
                if (currentRoom is TownSquare) {
                    (currentRoom as TownSquare).ringBell()
                } else println("There is nothing to ring")
            }

            "exit" -> {
                narrate("Goodbye.")
                exitProcess(0)
            }

            else -> narrate("I'm not sure what you want")
        }
    }
}


private fun makeYellow(message: String) = "\u001b[33;1m$message\u001b[0m"