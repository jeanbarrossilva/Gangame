package com.jeanbarrossilva.gangame.story.path.branched

import com.jeanbarrossilva.gangame.story.path.UnidentifiedPathException
import com.jeanbarrossilva.gangame.story.path.path
import kotlin.test.Test
import kotlin.test.assertFailsWith

internal class BranchedPathBuilderTests {
    @Test
    fun `GIVEN a builder without an ID WHEN building THEN it throws`() {
        assertFailsWith<UnidentifiedPathException> {
            BranchedPath.Builder().branch(path("branch")).build()
        }
    }
}