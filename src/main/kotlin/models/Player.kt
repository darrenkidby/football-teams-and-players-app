package models

data class Player(  var playerName: String,
                    var playerAge: Int,
                    var playerPosition: String,
                    var playerCost: String,
                    var playerWage: String,
                    var isPlayerYouth: Boolean = false,
                    var isPlayerRetired: Boolean = false)