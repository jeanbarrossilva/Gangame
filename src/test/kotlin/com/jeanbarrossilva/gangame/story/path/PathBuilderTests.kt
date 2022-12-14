package com.jeanbarrossilva.gangame.story.path

import kotlin.test.Test
import kotlin.test.assertFailsWith

internal class PathBuilderTests {
    @Test
    fun `GIVEN a builder without an ID WHEN building THEN it throws`() {
        assertFailsWith<UnidentifiedPathException> {
            Path.Builder.Default().build()
        }
    }
}