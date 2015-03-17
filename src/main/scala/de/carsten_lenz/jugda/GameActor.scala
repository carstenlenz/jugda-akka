package de.carsten_lenz.jugda

import akka.actor._

import scala.util.Random

/**
 * @author Carsten Lenz, AOE
 */
class GameActor()
  extends Actor
  with ActorLogging
  with URLEncodingSupport
  with PlayerActorsSupport {

  val random = new Random()

  var gameOngoing: Boolean = false
  var players: List[ActorRef] = List.empty

  def receive = {
    case StartGame(playerNames) if !gameOngoing =>
      players = playerNames.map { name =>
        context.actorOf(Props(new PlayerActor(self, random)), encodeName(name))
      }
      val ready: Ready = Ready(players)
      players foreach { p => p ! ready }
      gameOngoing = true
      randomPlayer() ! BallThrow(0)

    case TimeUp if gameOngoing =>
      players map { player =>
        player ! TimeUp
      }
      gameOngoing = false

    case IGotTheBall(player, passes) =>
      println(s"\nThe ball was passed $passes times")
      println(s"And player ${decodeName(player.path.name)} has the ball - Congratulations!")

    case say @ Say(message) => players foreach { p => p ! say}

    case _ => log.error("Message could not be handled")
  }
}
