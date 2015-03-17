package de.carsten_lenz.jugda


import java.net.{URLEncoder, URLDecoder}
import java.util.concurrent.TimeUnit

import akka.actor._
import akka.util.Timeout

import scala.util.Random

/**
 * @author Carsten Lenz, AOE
 */
class PlayerActor(val gameActor: ActorRef, val random: Random)
  extends Actor
  with URLEncodingSupport
  with PlayerActorsSupport
  with ActorLogging {

  implicit val timeout: Timeout = Timeout(5000, TimeUnit.SECONDS)

  var playing: Boolean = false
  var players: List[ActorRef] = List.empty

  def receive = {
    case Ready(allPlayers) =>
      players = allPlayers
      playing = true

    case BallThrow(passes) =>
//      Thread.sleep(200)
//      log.info("Ball was passed from {} to {}", decodeName(sender().path.name), decodeName(self.path.name))

      if (playing) {
        randomPlayer() ! BallThrow(passes + 1)
      } else {
        gameActor ! IGotTheBall(self, passes)
      }

    case TimeUp => playing = false

    case Say(message) => println(s"${decodeName(self.path.name)} says $message")
  }
}
