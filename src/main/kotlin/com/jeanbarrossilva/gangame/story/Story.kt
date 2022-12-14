package com.jeanbarrossilva.gangame.story

import com.jeanbarrossilva.gangame.story.extensions.getValue
import com.jeanbarrossilva.gangame.story.extensions.setValue
import com.jeanbarrossilva.gangame.story.path.Path
import com.jeanbarrossilva.gangame.story.path.flatten
import com.jeanbarrossilva.gangame.story.path.get
import com.jeanbarrossilva.gangame.story.path.ids
import com.jeanbarrossilva.gangame.story.path.indexOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class Story private constructor() {
    private val currentPathFlow = MutableStateFlow<Path?>(null)
    private var currentPath by currentPathFlow

    private val flattenedPaths
        get() = paths.flatten()

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
        return pathID in flattenedPaths.ids
    }

    fun advanceTo(pathID: String) {
        assertExists(pathID)
        assertFuturePath(pathID)
        assertDirectPath(pathID)
        currentPath = currentPath?.next(pathID) ?: paths[pathID]
    }

    private fun assertExists(pathID: String) {
        if (!contains(pathID)) {
            throw NonexistentPathException(pathID)
        }
    }

    private fun assertFuturePath(pathID: String) {
        if (isPastPath(pathID)) {
            throw PastPathException(pathID)
        }
    }

    private fun assertDirectPath(pathID: String) {
        if (isIndirectPath(pathID)) {
            throw IndirectPathException(currentPath?.id, pathID)
        }
    }

    private fun isPastPath(pathID: String): Boolean {
        return with(flattenedPaths) {
            indexOf(pathID) < indexOf(currentPath)
        }
    }

    private fun isIndirectPath(pathID: String): Boolean {
        val isNotCurrentPathsChild = currentPath?.isParentOf(pathID)?.not()
        val isNotRoot = paths[pathID] == null
        return isNotCurrentPathsChild ?: isNotRoot
    }
}