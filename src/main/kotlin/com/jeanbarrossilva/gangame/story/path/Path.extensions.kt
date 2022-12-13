package com.jeanbarrossilva.gangame.story.path

internal fun Collection<Path>.flatten(): List<Path> {
    return flatMap {
        it.toList()
    }
}