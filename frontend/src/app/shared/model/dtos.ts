export interface Game {
  name: string
  players: Player[]
  state: GameState
}

export enum GameState {
  RUNNING = "RUNNING",
  FINISHED = "FINISHED",
  CREATED = "CREATED"
}

export interface Player {
  identity: Identity
  playerState: PlayerState
  role: Role
  checked?: boolean
}

export interface Identity {
  name: string
}

export interface Profile {
  id?: number
  identity: Identity
  password_plain?: string
  password_encrypted?: number[]
  salt?: number[]
}

export enum Phases {
  ROLE, WEREWOLF, WAKEUP, DAY, NIGHT_FALL
}


export interface Vote {
  voteOf: Player
  voteFor: Player[]
}

export interface Voting {
  voters: Player[]
  candidates: Player[]
  votesPerPlayer: number
  numberOfSeats: number
}


export enum PlayerState {
  ALIVE, DYING, DEAD
}

export enum Role {
  WEREWOLF = "WEREWOLF", VILLAGER = "VILLAGER"
}

export enum OutboundMessage {
  ROLE, VOTING, DEAD_PLAYERS, GET_ACK
}
