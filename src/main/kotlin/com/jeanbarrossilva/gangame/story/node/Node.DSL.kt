package com.jeanbarrossilva.gangame.story.node

fun node(id: String, build: Node.Builder.Default.() -> Unit = { }): Node {
    return Node.Builder.Default().id(id).apply(build).build()
}