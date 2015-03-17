package de.carsten_lenz.jugda

import akka.actor.ActorRef

import scala.util.Random

/**
 * @author Carsten Lenz, AOE
 */
trait PlayerActorsSupport {
  def players: List[ActorRef]
  def random: Random

  def randomPlayer() = players(random.nextInt(players.size))
}
