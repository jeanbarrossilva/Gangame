package com.jeanbarrossilva.gangame.story.path

import com.jeanbarrossilva.gangame.story.path.branched.branchedPath
import kotlin.test.Test
import kotlin.test.assertContentEquals

internal class PathExtensionsTests {
    @Test
    fun `GIVEN various paths WHEN flattening them THEN they're non-continuous`() {
        assertContentEquals(
            listOf(path("a0"), path("b1"), path("c3"), path("d4")),
            listOf(branchedPath("a0") { branch(branchedPath("b1") { branch(path("c3")) }) }, path("d4")).flatten()
        )
    }
}