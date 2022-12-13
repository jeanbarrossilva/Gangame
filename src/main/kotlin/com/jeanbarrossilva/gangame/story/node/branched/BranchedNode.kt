package com.jeanbarrossilva.gangame.story.node.branched

import com.jeanbarrossilva.gangame.story.node.Node
import com.jeanbarrossilva.gangame.story.node.notifyAll

abstract class BranchedNode private constructor(): Node {
    protected abstract val branches: List<Node>

    class Builder internal constructor(): Node.Builder<Builder, BranchedNode>() {
        private val branches = mutableListOf<Node>()

        fun branch(branch: Node): Builder {
            return apply {
                branches.add(branch)
            }
        }

        override fun build(): BranchedNode {
            return object: BranchedNode() {
                override val id = getID()
                override val branches = this@Builder.branches.toList()

                override fun pointTo(id: String) {
                    assertContains(id)
                    listeners.notifyAll(id)
                }
            }
        }
    }

    operator fun contains(branchID: String): Boolean {
        return branchID in branches.map(Node::id)
    }

    protected fun assertContains(branchID: String) {
        if (!contains(branchID)) {
            throw NonexistentBranchException(parentNodeID = id, branchID)
        }
    }
}