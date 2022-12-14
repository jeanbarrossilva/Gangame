package com.jeanbarrossilva.gangame.story

internal fun story(build: Story.Builder.() -> Unit = { }): Story {
    return Story.Builder().apply(build).build()
}