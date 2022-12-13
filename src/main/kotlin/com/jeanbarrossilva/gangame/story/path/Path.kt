package com.jeanbarrossilva.gangame.story.path

abstract class Path {
    abstract val id: String
    abstract val next: Path?

    abstract class Builder<B: Builder<B, N>, N: Path> internal constructor() {
        private var id: String? = null

        class Default internal constructor(): Builder<Default, Path>() {
            override fun build(): Path {
                return object: Path() {
                    override val id = getID()
                    override val next: Path? = null
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
            return id ?: throw UnidentifiedPathException()
        }
    }

    override fun equals(other: Any?): Boolean {
        return other is Path && other.id == this.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    fun next(id: String): Path? {
        var current = next ?: return null
        while (current.id != id) {
            current = current.next ?: return null
        }
        return current
    }

    fun toList(): List<Path> {
        val accumulated = mutableListOf(this)
        var current = next
        while (current != null) {
            accumulated.add(current)
            current = current.next
        }
        return accumulated.toList()
    }
}