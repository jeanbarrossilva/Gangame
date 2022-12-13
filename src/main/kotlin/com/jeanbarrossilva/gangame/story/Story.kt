package com.jeanbarrossilva.gangame.story

import com.jeanbarrossilva.gangame.story.node.Node

abstract class Story private constructor() {
    internal abstract val nodes: List<Node>

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
}