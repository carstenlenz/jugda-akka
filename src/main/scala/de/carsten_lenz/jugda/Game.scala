package de.carsten_lenz.jugda

/**
 * @author Carsten Lenz, AOE
 */
object Game extends App {
  // tag::sample[]

  import akka.actor._
  import de.carsten_lenz.jugda._

  val system = ActorSystem("jugda")
  val game = system.actorOf(Props(new GameActor()), "game")

  game ! InitGame(NameReader.read("PATH/TO/CSV").toList)

  game ! StartGame

  Thread.sleep(5000)

  game ! TimeUp

  // end::sample[]
}
