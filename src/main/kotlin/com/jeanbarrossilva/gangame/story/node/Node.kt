package com.jeanbarrossilva.gangame.story.node

interface Node {
    val id: String

    companion object {
        fun identifiedAs(id: String): Node {
            return object: Node {
                override val id = id
            }
        }
    }
}