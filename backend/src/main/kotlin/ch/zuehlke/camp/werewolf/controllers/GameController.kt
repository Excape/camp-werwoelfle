package ch.zuehlke.camp.werewolf.controllers

import ch.zuehlke.camp.werewolf.domain.GameSettings
import ch.zuehlke.camp.werewolf.domain.Profile
import ch.zuehlke.camp.werewolf.service.LobbyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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

    @GetMapping("{gameName}/{playerName}")
    fun getPlayer(@PathVariable("gameName") gameName: String, @PathVariable("playerName") playerName: String): Any {
        val game = lobbyService.findGame(gameName);
        val player = game.players.firstOrNull { player ->
            player.identity.name == playerName
        };
        return player?: ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST)
    }

    @PostMapping("{gameName}/settings")
    fun updateSettings(@PathVariable("gameName") gameName: String, @RequestBody settings: GameSettings): Any {
        val game = lobbyService.findGame(gameName);
        game.settings = settings
        return game
    }

}