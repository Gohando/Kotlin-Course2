package com.gohando.nyethack

open class Room(private val name: String) {
    protected open val status = "Calm"
    open fun getName() = name
    open fun getRoomStatus() = "(Currently: $status)"
    open fun enterRoom() {
        narrate("There is nothing to do here")
    }
}

open class MonsterRoom(
    name: String,
    var monster: Monster? = if (name == "A Long Corridor") listOf(Draugr(), Goblin(), Werewolf()).random()
        else listOf(Draugr(), Goblin(), Werewolf(), Dragon()).random(),
    public override var status: String = if (monster != null) "Dangerous" else "Calm"
): Room(name) {

    override fun getRoomStatus(): String {
        return super.getRoomStatus() + "\n (Creature: ${monster?.description ?: "None"})"
    }

    override fun enterRoom() {
        if (monster == null) {
            super.enterRoom()
        } else {
            narrate("Danger is barking in this room")
        }
    }
}

open class TownSquare : Room("The Town Square") {
    override val status = "Bustling"
    private val bellSound = "AOAOAOAOAOA"
    final override fun enterRoom() {
        narrate("The villagers rally and cheer as the hero enters")
        ringBell()
    }
    public fun ringBell() {
        narrate("The bell tower  announces the hero's presence: $bellSound")
    }
}