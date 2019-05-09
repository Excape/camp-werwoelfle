export interface Game {
  name: string
  players: Player[]
  phases: Set<Phase>
  currentPhase: Phase
  isRunning: boolean
}

export interface Player {
  identity: Identity
  state: State
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

export interface Phase {
  name: string
  voting: Voting
}

export enum Phases {
  ROLE, WEREWOLF, WAKEUP, DAY
}


export interface Vote {
  voteOf: Player
  voteFor: Player[]
}

export interface Voting {
  voters: Player[]
  votees: Player[]
  numberOfVotesPerVoter: number
  votingResult?: VotingResult
}

export interface VotingResult {
  auditors: Player[]
  elected: Player[]
  showRoles: Boolean
}

export enum State {
  DEAD, ASLEEP, AWAKE
}

export enum Role {
  WEREWOLF, VILLAGER
}
