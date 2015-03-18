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
    case InitGame(playerNames) if players.isEmpty =>
      players = playerNames.map { name =>
        context.actorOf(Props(new PlayerActor(self, random)), encodeName(name))
      }
      makePlayersReady()

    case StartGame if !gameOngoing =>
      gameOngoing = true
      randomPlayer() ! BallThrow(0)

    case TimeUp if gameOngoing =>
      players foreach { _ ! TimeUp }

    case IGotTheBall(player, passes) =>
      println(s"\nThe ball was passed $passes times")
      println(s"And player ${decodeName(player.path.name)} has the ball - Congratulations!")
      println("This player will be removed from the List of active players")

      gameOngoing = false

      players = players.filterNot { _ == player }
      player ! PoisonPill
      makePlayersReady()

    case EndGame =>
      self ! PoisonPill // this kills all children as well

    case say @ Say(message) => players foreach { p => p ! say}

    case _ => log.error("Message could not be handled")
  }

  def makePlayersReady(): Unit = {
    val ready: Ready = Ready(players)
    players foreach { p => p ! ready}
  }
}
