package com.jeanbarrossilva.gangame.story

import com.jeanbarrossilva.gangame.story.path.Path
import com.jeanbarrossilva.gangame.story.path.flatten

abstract class Story private constructor() {
    internal abstract val paths: List<Path>

    internal var currentPath: Path? = null
        private set

    class Builder internal constructor() {
        private val paths = mutableListOf<Path>()

        fun path(path: Path): Builder {
            return apply {
                paths.add(path)
            }
        }

        fun build(): Story {
            return object: Story() {
                override val paths = this@Builder.paths.toList()
            }
        }
    }

    operator fun contains(pathID: String): Boolean {
        return pathID in paths.flatten().map(Path::id)
    }

    fun next(pathID: String) {
        assertContainsDirectPath(pathID)
        currentPath = currentPath?.next(pathID) ?: paths.firstOrNull()
    }

    private fun assertContainsDirectPath(pathID: String) {
        val contains = contains(pathID)
        val doesNotContain = !contains
        when {
            contains && !isDirectPath(pathID) -> throw IndirectPathException(pathID)
            doesNotContain -> throw NonexistentPathException(pathID)
        }
    }

    private fun isDirectPath(pathID: String): Boolean {
        return paths.find { it.id == pathID } != null
    }
}