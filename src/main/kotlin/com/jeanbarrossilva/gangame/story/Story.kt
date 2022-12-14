package com.jeanbarrossilva.gangame.story

import com.jeanbarrossilva.gangame.story.extensions.getValue
import com.jeanbarrossilva.gangame.story.extensions.setValue
import com.jeanbarrossilva.gangame.story.path.Path
import com.jeanbarrossilva.gangame.story.path.flatten
import com.jeanbarrossilva.gangame.story.path.get
import com.jeanbarrossilva.gangame.story.path.ids
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class Story private constructor() {
    private val currentPathFlow = MutableStateFlow<Path?>(null)
    private var currentPath by currentPathFlow

    internal abstract val paths: List<Path>

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

    fun getCurrentPathFlow(): StateFlow<Path?> {
        return currentPathFlow.asStateFlow()
    }

    operator fun contains(pathID: String): Boolean {
        return pathID in paths.flatten().ids
    }

    fun advanceTo(pathID: String) {
        assertContainsDirectPath(pathID)
        currentPath = currentPath?.next(pathID) ?: paths[pathID]
    }

    private fun assertContainsDirectPath(pathID: String) {
        val contains = contains(pathID)
        val doesNotContain = !contains
        val isCurrentPathsChild = currentPath?.isParentOf(pathID)
        val isRoot = paths[pathID] != null
        val isDirect = isCurrentPathsChild ?: isRoot
        when {
            contains && !isDirect -> throw IndirectPathException(pathID)
            doesNotContain -> throw NonexistentPathException(pathID)
        }
    }
}