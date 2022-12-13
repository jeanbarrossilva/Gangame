package com.jeanbarrossilva.gangame.story.node

abstract class Node {
    abstract val id: String
    abstract val next: Node?

    abstract class Builder<B: Builder<B, N>, N: Node> internal constructor() {
        private var id: String? = null

        class Default internal constructor(): Builder<Default, Node>() {
            override fun build(): Node {
                return object: Node() {
                    override val id = getID()
                    override val next: Node? = null
                }
            }
        }

        abstract fun build(): N

        @Suppress("UNCHECKED_CAST")
        fun id(id: String): B {
            return (this as B).apply {
                this.id = id
            }
        }

        protected fun getID(): String {
            return id ?: throw UnidentifiedNodeException()
        }
    }

    override fun equals(other: Any?): Boolean {
        return other is Node && other.id == this.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    fun next(id: String): Node? {
        var current = next ?: return null
        while (current.id != id) {
            current = current.next ?: return null
        }
        return current
    }

    fun toList(): List<Node> {
        val accumulated = mutableListOf(this)
        var current = next
        while (current != null) {
            accumulated.add(current)
            current = current.next
        }
        return accumulated.toList()
    }
}