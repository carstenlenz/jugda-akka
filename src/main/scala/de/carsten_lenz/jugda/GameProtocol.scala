package de.carsten_lenz.jugda

import akka.actor.ActorRef

case class StartGame(playerNames: List[String])
case class Ready(players: List[ActorRef])
case class BallThrow(passes: Int)
case object TimeUp
case class IGotTheBall(player: ActorRef, passes: Int)
case class Say(message: String)


