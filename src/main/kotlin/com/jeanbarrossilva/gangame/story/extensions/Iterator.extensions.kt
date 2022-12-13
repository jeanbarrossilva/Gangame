package com.jeanbarrossilva.gangame.story.extensions

internal fun <T> Iterator<T>.nextOrNull(): T? {
    return if (hasNext()) next() else null
}