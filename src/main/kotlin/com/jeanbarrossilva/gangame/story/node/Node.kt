package com.jeanbarrossilva.gangame.story.node

interface Node {
    val id: String

    class Builder internal constructor() {
        private var id: String? = null
        private val listeners = mutableListOf<OnPointingListener>()

        fun id(id: String): Builder {
            return apply {
                this.id = id
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