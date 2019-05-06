package ch.zuehlke.camp.werewolf.controllers


import ch.zuehlke.camp.werewolf.dtos.Game
import ch.zuehlke.camp.werewolf.dtos.Profile
import ch.zuehlke.camp.werewolf.service.LobbyService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class LobbyController(val lobbyService: LobbyService) {


    @GetMapping
    fun getGames(): List<Game> {
        return lobbyService.games
    }

    @PostMapping("create")
    fun createGame(@RequestBody profile: Profile, @RequestParam("gameName") gameName: String) {
        lobbyService.createGame(gameName, profile)
    }

    @PostMapping("join")
    fun joinGame(@RequestBody profile: Profile, @RequestParam("gameName") gameName: String) {
        lobbyService.joinGame(gameName, profile)
    }

}