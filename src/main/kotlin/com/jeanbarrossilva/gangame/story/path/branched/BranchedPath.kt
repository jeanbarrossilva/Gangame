package com.jeanbarrossilva.gangame.story.path.branched

import com.jeanbarrossilva.gangame.story.extensions.nextOrNull
import com.jeanbarrossilva.gangame.story.path.Path
import com.jeanbarrossilva.gangame.story.path.flatten
import com.jeanbarrossilva.gangame.story.path.ids

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

    override fun toString(): String {
        return "BranchedPath(id=$id, branches=$branches)"
    }

    override fun isParentOf(id: String): Boolean {
        return id in branches.ids
    }

    override fun toList(): List<Path> {
        return branches.flatMap(Path::toList).toMutableList().apply { add(0, this@BranchedPath) }.toList()
    }

    operator fun contains(id: String): Boolean {
        return id in branches.flatten().ids
    }
}