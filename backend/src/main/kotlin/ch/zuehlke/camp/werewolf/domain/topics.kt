package ch.zuehlke.camp.werewolf.domain

fun Game.toTopic(): String {
    return this.name
}

fun Role.toTopic(game: Game): String {
    return "${game.toTopic()}/${this.name}"
}

fun Player.toTopic(game:Game): String {
    return "${game.toTopic()}/${this.profile.id}"
}

fun Player.toTopic(gameName:String): String {
    return "${gameName}/${this.profile.id}"
}