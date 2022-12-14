package com.jeanbarrossilva.gangame.story.path

import com.jeanbarrossilva.gangame.story.path.branched.branchedPath
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

internal class PathExtensionsTests {
    @Test
    fun `GIVEN various paths WHEN flattening them THEN they're non-continuous`() {
        assertContentEquals(
            listOf(path("a0"), path("b1"), path("c3"), path("d4")),
            listOf(branchedPath("a0") { branch(branchedPath("b1") { branch(path("c3")) }) }, path("d4")).flatten()
        )
    }

    @Test
    fun `GIVEN a path ID WHEN getting the path from a collection with it THEN it isn't null`() {
        val path = path("expected")
        assertEquals(path, listOf(path, path("unexpected"))[path.id])
    }
}