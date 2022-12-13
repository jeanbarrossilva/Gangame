package com.jeanbarrossilva.gangame.story

import com.jeanbarrossilva.gangame.story.node.Node

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
        return nodeID in nodes.map(Node::id)
    }

    fun pointTo(nodeID: String) {
        assertContains(nodeID)
        currentNode = nodes.first { it.id == nodeID }
    }

    private fun assertContains(nodeID: String) {
        if (!contains(nodeID)) {
            throw NonexistentNodeException(nodeID)
        }
    }
}