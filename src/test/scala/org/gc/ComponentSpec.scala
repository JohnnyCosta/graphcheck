package org.gc

import org.gc.component.{Component, Network}
import org.gc.calculation.{CalculateAP, CalculateImportance}
import org.gc.graph.Graph
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test components
  *
  * @author : Joao Costa (joaocarlosfilho@gmail.com) on 01/01/17.
  */
@RunWith(classOf[JUnitRunner])
class ComponentSpec extends FlatSpec with Matchers {

  "Components graph" should "have expected articulation points" in {
    val g = new Network()

    val n = (0 to 7) map { g.node(_)}
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


  "Components network" should "have a calculated importance per component" in {
    val net = new Network()

    val comp = (0 to 8) map { net.node(_).asInstanceOf[Component]}
    comp(0).connectTo(comp(1))
    comp(1).connectTo(comp(2))
    comp(0).connectTo(comp(2))
    comp(0).connectTo(comp(3))
    comp(3).connectTo(comp(4))
    comp(4).connectTo(comp(5))
    comp(5).connectTo(comp(6))
    comp(6).connectTo(comp(4))
    comp(5).connectTo(comp(7))

    comp(5).failureRate = 30
    comp(5).userImportanceRate = 2

    comp(6).failureRate = 20

    comp(7).failureRate = 10
    comp(7).userImportanceRate = 12

    var calc = new CalculateImportance(net).updateNetwork(comp(0))

    calc.size should equal (8)

    comp(0).isPointOfFailure should equal (true)
    comp(0).score should equal (10)
    comp(1).isPointOfFailure should equal (false)
    comp(1).score should equal (0)
    comp(2).isPointOfFailure should equal (false)
    comp(2).score should equal (0)
    comp(3).isPointOfFailure should equal (true)
    comp(3).score should equal (10)
    comp(4).isPointOfFailure should equal (true)
    comp(4).score should equal (10)
    comp(5).isPointOfFailure should equal (true)
    comp(5).score should equal (22)
    comp(6).isPointOfFailure should equal (false)
    comp(6).score should equal (10)
    comp(7).isPointOfFailure should equal (false)
    comp(7).score should equal (12)

    comp(8).isPointOfFailure should equal (false)
    comp(8).score should equal (0)
  }


}
