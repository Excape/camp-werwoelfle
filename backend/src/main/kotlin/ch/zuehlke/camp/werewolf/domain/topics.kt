package ch.zuehlke.camp.werewolf.domain

fun Game.toTopic(): String {
    return this.name
}

fun Role.toTopic(game: Game): String {
    return "${game.toTopic()}/${this.name}"
}

fun Player.toTopic(game:Game): String {
    return "${game.toTopic()}/${this.identity.name}"
}

fun Player.toTopic(gameName:String): String {
    return "$gameName/${this.identity.name}"
}

fun OutboundMessage.toTopic(gameName: String, player: Player): String {
    return "${player.toTopic(gameName)}/${this.type}"
}