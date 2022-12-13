package com.jeanbarrossilva.gangame.story.node

import com.jeanbarrossilva.gangame.story.node.branched.branchedNode
import kotlin.test.Test
import kotlin.test.assertContentEquals

internal class NodeExtensionsTests {
    @Test
    fun `GIVEN various nodes WHEN flattening them THEN they're non-continuous`() {
        assertContentEquals(
            listOf(node("a0"), node("b1"), node("c3"), node("d4")),
            listOf(branchedNode("a0") { branch(branchedNode("b1") { branch(node("c3")) }) }, node("d4")).flatten()
        )
    }
}