package org.gc.calculation

import com.typesafe.scalalogging.Logger
import org.gc.component.{Component, Network}

import scala.collection.immutable.HashSet

/**
  * Importance calculator for the network
  *
  * Created by joao on 01/02/17.
  */
class CalculateImportance(val network: Network) {

  val log = Logger(classOf[CalculateImportance])

  val failureThreshold = 20
  val failureScore = 10
  val pointOfFailureScore = 10

  var subNetwork = new HashSet[Component]

  def updateNetwork(startComponent: Component): Set[Component] = {

    // Update only the subnet
    subNetwork = new HashSet[Component]
    DFS(startComponent)

    val aps = new CalculateAP(network).findArticulationPoints(startComponent)
    for (n <- aps) {
      n.asInstanceOf[Component].isPointOfFailure = true
    }

    for (n <- subNetwork) {
      scoreCalculator(n.asInstanceOf[Component])
    }

    return subNetwork

  }

  private def scoreCalculator(comp: Component) = {
    var finalScore = 0
    if (comp.isPointOfFailure) {
      finalScore += pointOfFailureScore
    }
    if (comp.failureRate >= failureThreshold) {
      finalScore += failureScore
    }
    finalScore += comp.userImportanceRate
    comp.score = finalScore
  }

  private def DFS(v: Component) {

    subNetwork += v

    for (x <- v.adjacentNodes()) {
      if (subNetwork.find(x.equals).isEmpty) {
        DFS(x.asInstanceOf[Component])
      }
    }
  }

}
