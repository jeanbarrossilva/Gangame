package com.jeanbarrossilva.gangame.story.node

import kotlin.test.Test
import kotlin.test.assertFailsWith

internal class NodeBuilderTests {
    @Test
    fun `GIVEN a builder without an ID WHEN building THEN it throws`() {
        assertFailsWith<UnidentifiedNodeException> {
            Node.Builder.Default().build()
        }
    }
}