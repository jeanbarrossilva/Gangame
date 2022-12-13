package com.jeanbarrossilva.gangame.story.node.branched

import com.jeanbarrossilva.gangame.story.node.Node

abstract class BranchedNode private constructor(): Node {
    protected abstract val branches: List<Node>
    protected abstract val listeners: List<OnPointingListener>

    class Builder {
        private var id: String? = null
        private val branches = mutableListOf<Node>()
        private val listeners = mutableListOf<OnPointingListener>()

        fun id(id: String): Builder {
            return apply {
                this.id = id
            }
        }

        fun branch(id: String): Builder {
            val branch = Node.identifiedAs(id)
            return apply { branches.add(branch) }
        }

        fun listen(listener: OnPointingListener): Builder {
            return apply {
                listeners.add(listener)
            }
        }

        fun build(): BranchedNode {
            return object: BranchedNode() {
                override val id = requireNotNull(this@Builder.id)
                override val branches = this@Builder.branches.toList()
                override val listeners = this@Builder.listeners.toList()
            }
        }
    }

    fun pointTo(branchID: String) {
        assertExistenceOfBranchIdentifiedAs(branchID)
        notifyListenersOfPointingTo(branchID)
    }

    private fun assertExistenceOfBranchIdentifiedAs(branchID: String) {
        val isBranchNonexistent = branchID !in branches.map(Node::id)
        if (isBranchNonexistent) {
            throw IllegalArgumentException("Cannot point to a nonexistent \"$branchID\" branch.")
        }
    }

    private fun notifyListenersOfPointingTo(branchID: String) {
        val branch = branches.single { it.id == branchID }
        listeners.forEach { it.onPointTo(branch) }
    }
}