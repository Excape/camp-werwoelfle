package ch.zuehlke.camp.werewolf

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class WerewolfApplication

fun main(args: Array<String>) {
    runApplication<WerewolfApplication>(*args)
}
