package org.gc.component

import org.gc.graph.Node

/**
  * Abstraction for components
  *
  * Created by joao on 01/02/17.
  */
class Component(override val id: Int) extends Node(id) {

  var userImportanceRate = 0 // Scale 0 to 10
  var failureRate = 0
  var isPointOfFailure = false // Calculated via AP
  var score = 0 // Final component score

}
