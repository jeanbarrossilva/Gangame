package com.jeanbarrossilva.gangame.story

import com.jeanbarrossilva.gangame.story.node.branched.branchedNode
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
        val namedMattBranch = node("named-matt")
        val namedNatBranch = node("named-nat")
        val namedNode = branchedNode("named") {
            branch(namedMattBranch)
            branch(namedNatBranch)
        }
        val story = story {
            node(onceUponATimeNode)
            node(thereWasARatNode)
            node(namedNode)
        }
        assertContentEquals(listOf(onceUponATimeNode, thereWasARatNode, namedNode), story.nodes)
    }

    @Test
    fun `GIVEN a node WHEN pointing to it THEN it is the current one`() {
        val node = node("preface")
        val story = story { node(node) }
        story.next(node.id)
        assertEquals(node, story.currentNode)
    }

    @Test
    fun `GIVEN an indirect node WHEN trying to find it THEN it throws`() {
        assertFailsWith<IndirectNodeException> {
            story {
                node(node("intro"))
                node(node("resolution"))
                node(branchedNode("extras") { branch(node("credits")) })
            }
                .next("credits")
        }
    }
}