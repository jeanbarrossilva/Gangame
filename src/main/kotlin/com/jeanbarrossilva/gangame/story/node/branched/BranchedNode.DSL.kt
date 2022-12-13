package com.jeanbarrossilva.gangame.story.node.branched

internal fun branchedNode(build: BranchedNode.Builder.() -> Unit): BranchedNode {
    val builder = BranchedNode.Builder()
    return builder.apply(build).build()
}