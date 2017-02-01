package org.gc.graph

/**
  * Node abstraction
  *
  * @author : Joao Costa (joaocarlosfilho@gmail.com) on 01/01/17.
  */
class Node(val id: Int) {
  var connectedNodes: Set[Node] = Set.empty

  def connectTo(node: Node) {
    if (connectedNodes.find(node.equals).isEmpty) {
      connectedNodes += node
      node.connectTo(this)
    }
  }

  def adjacentNodes(): Set[Node] = {
    connectedNodes
  }


  override def toString = s"NNode($id)"
}
