package org.gc

import org.gc.calculation.CalculateAP
import org.gc.graph.Graph
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test graph calculator
  *
  * @author : Joao Costa (joaocarlosfilho@gmail.com) on 01/01/17.
  */
@RunWith(classOf[JUnitRunner])
class GraphSpec extends FlatSpec with Matchers {

  "First graph" should "have expected articulation points" in {
    val g = new Graph()

    val n = (1 to 8) map { g.node(_)}
    n(0).connectTo(n(1))
    n(1).connectTo(n(2))
    n(0).connectTo(n(2))
    n(0).connectTo(n(3))
    n(3).connectTo(n(4))
    n(4).connectTo(n(5))
    n(5).connectTo(n(6))
    n(6).connectTo(n(4))
    n(5).connectTo(n(7))

    val aps = new CalculateAP(g).findArticulationPoints(n(0))

    aps should equal(Set(n(0), n(3), n(4), n(5)))
  }

  "More complex graph" should "have expected articulation points" in {
    val g = new Graph()

    (1 to 10) map { g.node(_)}

    val n = (0 to 12) map { g.node(_)}
    n(0).connectTo(n(1))
    n(0).connectTo(n(7))
    n(1).connectTo(n(2))
    n(1).connectTo(n(7))
    n(2).connectTo(n(3))
    n(2).connectTo(n(10))
    n(2).connectTo(n(4))
    n(3).connectTo(n(5))
    n(3).connectTo(n(8))
    n(5).connectTo(n(8))
    n(8).connectTo(n(10))
    n(4).connectTo(n(6))
    n(4).connectTo(n(7))
    n(7).connectTo(n(9))
    n(7).connectTo(n(11))
    n(9).connectTo(n(11))
    n(11).connectTo(n(12))

    val aps = new CalculateAP(g).findArticulationPoints(n(0))

    aps should equal(Set(n(2), n(4), n(7), n(11)))
  }
}
