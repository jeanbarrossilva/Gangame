package com.jeanbarrossilva.gangame.story.path

fun path(id: String, build: Path.Builder.Default.() -> Unit = { }): Path {
    return Path.Builder.Default().id(id).apply(build).build()
}