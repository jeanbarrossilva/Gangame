package com.jeanbarrossilva.gangame.story.node

fun node(build: Node.Builder.() -> Unit): Node {
    return Node.Builder().apply(build).build()
}