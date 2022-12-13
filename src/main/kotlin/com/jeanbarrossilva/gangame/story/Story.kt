package com.jeanbarrossilva.gangame.story

import com.jeanbarrossilva.gangame.story.node.Node
import com.jeanbarrossilva.gangame.story.node.flatten

abstract class Story private constructor() {
    internal abstract val nodes: List<Node>

    internal var currentNode: Node? = null
        private set

    class Builder internal constructor() {
        private val nodes = mutableListOf<Node>()

        fun node(node: Node): Builder {
            return apply {
                nodes.add(node)
            }
        }

        fun build(): Story {
            return object: Story() {
                override val nodes = this@Builder.nodes.toList()
            }
        }
    }

    operator fun contains(nodeID: String): Boolean {
        return nodeID in nodes.flatten().map(Node::id)
    }

    fun next(nodeID: String) {
        assertDirectExistence(nodeID)
        currentNode = currentNode?.next(nodeID) ?: nodes.firstOrNull()
    }

    private fun assertDirectExistence(nodeID: String) {
        val contains = contains(nodeID)
        val doesNotContain = !contains
        when {
            contains && !isDirectNode(nodeID) -> throw IndirectNodeException(nodeID)
            doesNotContain -> throw NonexistentNodeException(nodeID)
        }
    }

    private fun isDirectNode(nodeID: String): Boolean {
        return nodes.find { it.id == nodeID } != null
    }
}