package com.gohando.nyethack

open class Room(private val name: String) {
    protected open val status = "Calm"
    fun description() = "$name (Currently: $status)"
    open fun enterRoom() {
        narrate("There is nothing to do here")
    }
}

open class TownSquare : Room("The Town Square") {
    override val status = "Bustling"
    private val bellSound = "AOAOAOAOAOA"
    final override fun enterRoom() {
        narrate("The villagers rally and cheer as the hero enters")
        ringBell()
    }
    fun ringBell() {
        narrate("The bell tower  announces the hero's presence: $bellSound")
    }
}