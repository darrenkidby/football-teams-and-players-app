package models

data class Player(  var playerId: Int = 0,
                    var playerName: String,
                    var playerAge: Int,
                    var playerPosition: String,
                    var playerCost: String,
                    var playerWage: String,
                    var isPlayerYouth: Boolean = false,
                    var isPlayerRetired: Boolean = false){

    override fun toString(): String {
        val playerRetire = if (isPlayerRetired) "Yes" else "No"
        val playerYouth = if (isPlayerYouth) "Yes" else "No"
        return "$playerId: $playerName, Age($playerAge), Position($playerPosition), Cost($playerCost), Wage($playerWage), Retired?($playerRetire), Youth?($playerYouth)"
    }

}