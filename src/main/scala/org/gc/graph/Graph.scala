package org.gc.graph

/**
  * Graph abstraction
  *
  * @author : Joao Costa (joaocarlosfilho@gmail.com) on 01/01/17.
  */
class Graph {

  var nodes: Set[Node] = Set.empty

  def node(id: Int): Node = {
    val node = new Node(id)
    nodes += node
    node
  }
}
