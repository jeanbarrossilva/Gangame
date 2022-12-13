package com.jeanbarrossilva.gangame.story.node.branched

import com.jeanbarrossilva.gangame.story.node.Node
import com.jeanbarrossilva.gangame.story.node.OnPointingListener
import com.jeanbarrossilva.gangame.story.node.notifyAll

abstract class BranchedNode private constructor(): Node {
    protected abstract val branches: List<Node>

    class Builder internal constructor() {
        private var id: String? = null
        private val branches = mutableListOf<Node>()
        private val listeners = mutableListOf<OnPointingListener>()

        fun id(id: String): Builder {
            return apply {
                this.id = id
            }
        }

        fun branch(branch: Node): Builder {
            return apply { branches.add(branch) }
        }

        fun onPointing(listener: OnPointingListener): Builder {
            return apply {
                listeners.add(listener)
            }
        }

        fun build(): BranchedNode {
            return object: BranchedNode() {
                override val id = requireNotNull(this@Builder.id)
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