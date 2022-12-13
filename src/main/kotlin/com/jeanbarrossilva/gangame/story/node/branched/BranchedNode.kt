package com.jeanbarrossilva.gangame.story.node.branched

import com.jeanbarrossilva.gangame.story.extensions.nextOrNull
import com.jeanbarrossilva.gangame.story.node.Node
import com.jeanbarrossilva.gangame.story.node.flatten

abstract class BranchedNode private constructor(private val branches: List<Node>): Node() {
    private val branchesIterator = branches.iterator()

    override val next
        get() = branchesIterator.nextOrNull()

    class Builder internal constructor(): Node.Builder<Builder, BranchedNode>() {
        private val branches = mutableListOf<Node>()

        fun branch(branch: Node): Builder {
            return apply {
                branches.add(branch)
            }
        }

        override fun build(): BranchedNode {
            return object: BranchedNode(this@Builder.branches.toList()) {
                override val id = getID()
            }
        }
    }

    operator fun contains(branchID: String): Boolean {
        return branchID in branches.flatten().map(Node::id)
    }
}