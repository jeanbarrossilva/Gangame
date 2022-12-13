package com.jeanbarrossilva.gangame.story.node

interface Node {
    val id: String

    abstract class Builder<B: Builder<B, N>, N: Node> internal constructor() {
        private var id: String? = null

        protected val listeners = mutableListOf<OnPointingListener>()

        class Default internal constructor(): Builder<Default, Node>() {
            override fun build(): Node {
                return object: Node {
                    override val id = getID()

                    override fun pointTo(id: String) {
                        return listeners.notifyAll(id)
                    }
                }
            }
        }

        abstract fun build(): N

        fun id(id: String): B {
            return self {
                this.id = id
            }
        }

        fun onPointing(listener: OnPointingListener): B {
            return self {
                listeners.add(listener)
            }
        }

        protected fun getID(): String {
            return id ?: throw UnidentifiedNodeException()
        }

        @Suppress("UNCHECKED_CAST")
        private fun self(operation: B.() -> Unit): B {
            return (this as B).apply(operation)
        }
    }

    fun pointTo(id: String)
}