package com.jeanbarrossilva.gangame.story.node

fun node(id: String, build: Node.Builder.() -> Unit = { }): Node {
    return Node.Builder().id(id).apply(build).build()
}