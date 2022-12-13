package com.jeanbarrossilva.gangame.story.node.branched

internal fun branchedNode(id: String, build: BranchedNode.Builder.() -> Unit = { }): BranchedNode {
    val builder = BranchedNode.Builder()
    return builder.id(id).apply(build).build()
}