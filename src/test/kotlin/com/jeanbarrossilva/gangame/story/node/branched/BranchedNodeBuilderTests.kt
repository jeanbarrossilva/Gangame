package com.jeanbarrossilva.gangame.story.node.branched

import com.jeanbarrossilva.gangame.story.node.UnidentifiedNodeException
import com.jeanbarrossilva.gangame.story.node.node
import kotlin.test.Test
import kotlin.test.assertFailsWith

internal class BranchedNodeBuilderTests {
    @Test
    fun `GIVEN a builder without an ID WHEN building THEN it throws`() {
        assertFailsWith<UnidentifiedNodeException> {
            BranchedNode.Builder().branch(node("branch")).build()
        }
    }
}