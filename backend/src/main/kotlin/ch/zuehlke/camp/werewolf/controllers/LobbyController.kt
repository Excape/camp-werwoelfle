package ch.zuehlke.camp.werewolf.controllers


import ch.zuehlke.camp.werewolf.domain.Game
import ch.zuehlke.camp.werewolf.domain.Profile
import ch.zuehlke.camp.werewolf.service.LobbyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class LobbyController(val lobbyService: LobbyService) {


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

    @PostMapping("leave")
    fun leaveGame(@RequestBody profile: Profile, @RequestParam("gameName") gameName: String): ResponseEntity<Game> {
        val game: Game
        try {
            game = lobbyService.leaveGame(gameName, profile)
        } catch (exception: IllegalArgumentException) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
        return ResponseEntity(game, HttpStatus.OK)
    }

    @PostMapping("delete")
    fun deleteGame(@RequestBody profile: Profile, @RequestParam("gameName") gameName: String): ResponseEntity<Game> {
        val game: Game
        try {
            game = lobbyService.deleteGame(gameName)
        } catch (exception: IllegalArgumentException) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
        return ResponseEntity(game, HttpStatus.OK)
    }

    @GetMapping("start")
    fun startGame(@RequestParam("gameName") gameName: String): ResponseEntity<Unit> {
        try {
            this.lobbyService.startGame(gameName)
        } catch (exception: IllegalArgumentException) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
        return ResponseEntity(HttpStatus.OK);
    }
}