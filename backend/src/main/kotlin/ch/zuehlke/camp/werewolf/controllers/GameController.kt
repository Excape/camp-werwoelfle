package ch.zuehlke.camp.werewolf.controllers

import ch.zuehlke.camp.werewolf.service.LobbyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/game/")
class GameController(val lobbyService: LobbyService) {


    @GetMapping("{gameName}/{playerName}/role")
    fun getRole(@PathVariable("gameName") gameName: String, @PathVariable("playerName") playerName: String): Any {
        val game = lobbyService.findGame(gameName);
        val player = game.players.firstOrNull { player ->
            player.identity.name == playerName
        };
        return player?.role?: ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST)
    }

    @GetMapping("{gameName}/{playerName}/")
    fun getPlayer(@PathVariable("gameName") gameName: String, @PathVariable("playerName") playerName: String): Any {
        val game = lobbyService.findGame(gameName);
        val player = game.players.firstOrNull { player ->
            player.identity.name == playerName
        };
        return player?: ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST)
    }

}