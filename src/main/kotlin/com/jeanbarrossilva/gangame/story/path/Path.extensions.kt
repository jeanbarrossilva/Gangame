package com.jeanbarrossilva.gangame.story.path

internal val Collection<Path>.ids
    get() = map(Path::id)

internal fun Collection<Path>.flatten(): List<Path> {
    return flatMap {
        it.toList()
    }
}

internal operator fun Collection<Path>.get(id: String): Path? {
    return find {
        it.id == id
    }
}

internal fun Collection<Path>.indexOf(id: String): Int {
    return indexOf(get(id))
}