package de.carsten_lenz.jugda

import akka.actor.ActorRef

case class InitGame(playerNames: List[String])

case object StartGame

case class Ready(players: List[ActorRef])

case class BallThrow(passes: Int)

case object TimeUp

case class IGotTheBall(player: ActorRef, passes: Int)

case class Say(message: String)

case object EndGame


