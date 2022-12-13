package com.jeanbarrossilva.gangame.story.node

internal fun Collection<Node>.flatten(): List<Node> {
    return flatMap {
        it.toList()
    }
}