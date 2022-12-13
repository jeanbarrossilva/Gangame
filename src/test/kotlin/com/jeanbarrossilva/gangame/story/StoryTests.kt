package com.jeanbarrossilva.gangame.story

import com.jeanbarrossilva.gangame.story.node.node
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

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

    @Test
    fun `GIVEN a node WHEN pointing to it THEN it is the current one`() {
        val node = node("preface")
        val story = story { node(node) }
        story.pointTo(node.id)
        assertEquals(node, story.currentNode)
    }

    @Test
    fun `GIVEN a nonexistent node WHEN pointing to it THEN it throws`() {
        assertFailsWith<NonexistentNodeException> {
            story {
                node(node("intro"))
                node(node("resolution"))
            }
                .pointTo("climax")
        }
    }
}