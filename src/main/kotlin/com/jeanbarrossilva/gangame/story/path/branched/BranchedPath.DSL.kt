package com.jeanbarrossilva.gangame.story.path.branched

internal fun branchedPath(id: String, build: BranchedPath.Builder.() -> Unit = { }): BranchedPath {
    val builder = BranchedPath.Builder()
    return builder.id(id).apply(build).build()
}