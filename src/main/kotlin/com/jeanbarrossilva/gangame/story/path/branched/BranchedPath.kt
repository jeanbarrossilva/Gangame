package com.jeanbarrossilva.gangame.story.path.branched

import com.jeanbarrossilva.gangame.story.extensions.nextOrNull
import com.jeanbarrossilva.gangame.story.path.Path
import com.jeanbarrossilva.gangame.story.path.flatten

abstract class BranchedPath private constructor(private val branches: List<Path>): Path() {
    private val branchesIterator = branches.iterator()

    override val next
        get() = branchesIterator.nextOrNull()

    class Builder internal constructor(): Path.Builder<Builder, BranchedPath>() {
        private val branches = mutableListOf<Path>()

        fun branch(branch: Path): Builder {
            return apply {
                branches.add(branch)
            }
        }

        override fun build(): BranchedPath {
            return object: BranchedPath(this@Builder.branches.toList()) {
                override val id = getID()
            }
        }
    }

    operator fun contains(branchID: String): Boolean {
        return branchID in branches.flatten().map(Path::id)
    }
}