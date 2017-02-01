package org.gc.component

import org.gc.graph.{Graph, Node}

/**
  * Abstration of a network of components
  *
  * Created by joao on 01/02/17.
  */
class Network extends Graph{

  override def node(id: Int): Node = {
    val node = new Component(id)
    nodes += node
    node
  }
}
