package org.gc.calculation

import com.typesafe.scalalogging.Logger
import org.gc.graph.{Graph, Node}

import scala.collection.immutable.{HashMap, HashSet}


/**
  * Calculator for finding articulation points
  *
  * @author : Joao Costa (joaocarlosfilho@gmail.com) on 01/01/17.
  */
class CalculateAP(val graph: Graph) {

  val log = Logger(classOf[CalculateAP])

  var visited = new HashSet[Node]
  var articulationPoints = new HashSet[Node]
  var number = new HashMap[Node, Int]
  var low = new HashMap[Node, Int]
  var parent = new HashMap[Node, Node]
  var counter: Int = 0

  def findArticulationPoints(startNode: Node): Set[Node] = {

    visited = new HashSet[Node]
    articulationPoints = new HashSet[Node]
    number = new HashMap[Node, Int]
    low = new HashMap[Node, Int]
    parent = new HashMap[Node, Node]
    counter = 0

    DFS(startNode)

    articulationPoints
  }

  private def DFS(v: Node) {

    log.debug(s"Visiting ${v.id}")

    var childCount = 0
    var isArticulationPoint = false

    visited += v
    number += (v -> counter)
    low += (v -> counter)
    counter += 1

    for (x<- v.adjacentNodes()) {
      if (x == parent.get(v)) {
        log.debug("Adjacent is a parent, ignoring")
      } else {
        if (visited.find(x.equals).isEmpty) {
          childCount += 1
          parent += (x -> v)
          log.debug("Adjacent not yet visited")
          DFS(x)

          low += (v -> Math.min(low.get(v).get, low.get(x).get))

          if (low.get(x).get>=number.get(v).get){
            isArticulationPoint = true
            log.debug("It is a articulation point")
          }

        } else {
          log.debug("Adjacent already visited, computing low")
          low += (v -> Math.min(low.get(v).get, number.get(x).get))
        }
      }
    }
    if ((parent.get(v).isEmpty && childCount >= 2) || parent.get(v).isDefined && isArticulationPoint) {
      articulationPoints += v
    }
  }

}
