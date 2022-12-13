package com.jeanbarrossilva.gangame.story

import com.jeanbarrossilva.gangame.story.node.node
import kotlin.test.Test
import kotlin.test.assertContentEquals

internal class StoryTests {
    @Test
    fun `GIVEN various nodes WHEN adding them THEN they're ordered`() {
        val onceUponATimeNode = node("once-upon-a-time")
        val thereWasARatNode = node("there-was-a-rat")
        val namedMattNode = node("named-matt")
        val story = story {
            node(onceUponATimeNode)
            node(thereWasARatNode)
            node(namedMattNode)
        }
        assertContentEquals(listOf(onceUponATimeNode, thereWasARatNode, namedMattNode), story.nodes)
    }
}