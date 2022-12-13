package com.jeanbarrossilva.gangame.story.node

interface Node {
    val id: String

    class Builder {
        private var id: String? = null
        private val listeners = mutableListOf<OnPointingListener>()

        fun id(id: String): Builder {
            return apply {
                this.id = id
            }
        }

        fun onPointing(listener: OnPointingListener): Builder {
            return apply {
                listeners.add(listener)
            }
        }

        fun build(): Node {
            return object: Node {
                override val id = this@Builder.id ?: throw UnidentifiedNodeException()

                override fun pointTo(id: String) {
                    listeners.notifyAll(id)
                }
            }
        }
    }

    fun pointTo(id: String)
}