import  java.io.File
import kotlin.random.Random
import kotlin.random.nextInt

private const val TAVERN_MASTER = "Baba Tanya"
private const val TAVERN_NAME = "$TAVERN_MASTER's Folly"

private val firstNames = setOf("Fotik", "Rubail", "Sophie", "Tariq")
private val lastNames = setOf("Ironfoot", "Beansworth", "Saggins", "Downlooker")

private val menuData = File("data/tavern-menu-data.txt")
    .readText()
    .split("\n")

private val menuItems = List(menuData.size) { index ->
    val (_, name, _) = menuData[index].split(",")
    name
}

private val menuItemPrices: Map<String, Double> = List(menuData.size) {index ->
    val (_, name, price) = menuData[index].split(",")
    name to price.toDouble()
}.toMap()

private val menuItemTypes: Map<String, String> = List(menuData.size) { index ->
    val (type, name, _) = menuData[index].split(",")
    name to type
}.toMap()

private val menuFormatted = MutableList(menuData.size) { index ->
    val (type, name, price) = menuData[index].split(",")
    "$type;$name,$price"
}
fun findMaxLengthOfMenuItem(menuItems: List<String>):Int {
    var maxLengthOfMenuItem = menuItems[0].length - menuItems[0].substringBefore(";").length
    for (item in menuItems) {
        if (item.length - item.substringBefore(";").length > maxLengthOfMenuItem) {
            maxLengthOfMenuItem = item.length - item.substringBefore(";").length
        }
    }
    return maxLengthOfMenuItem
}
val maxLengthOfMenuItem = findMaxLengthOfMenuItem(menuFormatted)

fun visitTavern() {
    println(maxLengthOfMenuItem)
    narrate("$heroName enters $TAVERN_NAME")
    narrate("The hero picks up a menu:")
    narrate("*** Welcome to $TAVERN_MASTER's Folly ***")
    val menuTypes: List<String> = List(menuFormatted.size) { index ->
        val item = menuFormatted[index].substringBefore(";")
        item
    }
    menuFormatted.sort()
    val tempMenuTypes: MutableSet<String> = menuTypes.toMutableSet()
    menuFormatted.forEach {
        val currentType = it.substringBefore(";")
        if (tempMenuTypes.contains(currentType)) {
            val neededSpaces = maxLengthOfMenuItem - currentType.length - 4
            print(" ".repeat(neededSpaces / 2))
            print("~[$currentType]~")
            println(" ".repeat(neededSpaces / 2))
            tempMenuTypes.remove(currentType)
        }
        val neededDots = maxLengthOfMenuItem + 4 - it.length + currentType.length
        val dottedString = ".".repeat(neededDots)
        println(it.replace(",", dottedString).substringAfter(";"))
    }

    val patrons: MutableSet<String> = mutableSetOf()
    val patronGold: MutableMap<String, Double> = mutableMapOf(
        TAVERN_MASTER to 86.00,
        heroName to 4.50
    )
    while (patrons.size < 5) {
        val patronName = "${firstNames.random()} ${lastNames.random()}"
        patrons += patronName
        patronGold += patronName to Random.nextInt(10..30).toDouble()
    }
    narrate("$heroName sees several patrons in the tavern:")
    narrate(patrons.joinToString())
    repeat(3) {
        val orderItemsNames: MutableList<String> = mutableListOf()
        repeat(Random.nextInt(1..3)) {
            orderItemsNames += menuItems.random()
        }
        placeOrder(patrons.random(), orderItemsNames, patronGold)
    }
    displayPatronBalances(patronGold)
}
private fun displayPatronBalances(patronGold: Map<String, Double>) {
    narrate("$heroName intuitively knows how much money each patron has")
    patronGold.forEach { (patron, balance) ->
        narrate("$patron has ${"%.2f".format(balance)} gold")
    }
}
private fun placeOrder(
    patronName: String,
    orderItemsNames: List<String>,
    patronGold: MutableMap<String, Double>
) {
    val orderPrice: Double = orderItemsNames.sumOf { menuItemPrices.getOrDefault(it, 0.0) }
    narrate("$patronName speaks with $TAVERN_MASTER to place an order")
    if (orderPrice <= patronGold.getOrDefault(patronName, 0.0)) {
        orderItemsNames.forEach {
            val action = when (menuItemTypes[it]) {
                "shandy", "elixir" -> "poisons"
                "meal" -> "serves"
                else -> "hands"
            }
            narrate("$TAVERN_MASTER $action $patronName a $it")
        }
        narrate("$patronName pays $TAVERN_MASTER $orderPrice gold")
        patronGold[patronName] = patronGold.getValue(patronName) - orderPrice
        patronGold[TAVERN_MASTER] = patronGold.getValue(TAVERN_MASTER) + orderPrice
    } else {
        narrate("$TAVERN_MASTER says, \"You need more gold for your order\"")
    }
}