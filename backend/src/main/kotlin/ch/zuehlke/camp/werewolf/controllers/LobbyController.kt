package ch.zuehlke.camp.werewolf.controllers


import ch.zuehlke.camp.werewolf.domain.Game
import ch.zuehlke.camp.werewolf.domain.Profile
import ch.zuehlke.camp.werewolf.service.LobbyService
import ch.zuehlke.camp.werewolf.service.MessageService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class LobbyController(val lobbyService: LobbyService, val messageService: MessageService) {


    @GetMapping
    fun getGames(): List<Game> {
        return lobbyService.games
    }

    @PostMapping("create")
    fun createGame(@RequestBody profile: Profile, @RequestParam("gameName") gameName: String): Game {
        return lobbyService.createGame(gameName, profile)
    }

    @PostMapping("join")
    fun joinGame(@RequestBody profile: Profile, @RequestParam("gameName") gameName: String): ResponseEntity<Game> {
        val game: Game
        try {
            game = lobbyService.joinGame(gameName, profile)
        } catch (exception: IllegalArgumentException) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
        return ResponseEntity(game, HttpStatus.OK)
    }

}