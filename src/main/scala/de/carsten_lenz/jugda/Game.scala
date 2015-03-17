package de.carsten_lenz.jugda

/**
 * @author Carsten Lenz, AOE
 */
object Game extends App {

  import akka.actor._
  import de.carsten_lenz.jugda._
  import java.net.URLEncoder

  val system = ActorSystem("jugda")
  val game = system.actorOf(Props(new GameActor()), "game")

  game ! StartGame(List("Sebastian", "Carsten", "Jan", "Das Niko", "Wirrer, Name :)"))

  Thread.sleep(5000)

  game ! TimeUp
}
