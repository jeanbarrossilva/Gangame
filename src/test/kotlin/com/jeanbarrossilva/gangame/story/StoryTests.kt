package com.jeanbarrossilva.gangame.story

import com.jeanbarrossilva.gangame.story.path.Path
import com.jeanbarrossilva.gangame.story.path.branched.branchedPath
import com.jeanbarrossilva.gangame.story.path.path
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

internal class StoryTests {
    @Test
    fun `GIVEN an empty story WHEN getting the current path THEN it is null`() {
        assertNull(story().getCurrentPathFlow().value)
    }

    @Test
    fun `GIVEN various paths WHEN adding them THEN they're ordered`() {
        val onceUponATimePath = path("once-upon-a-time")
        val thereWasARatPath = path("there-was-a-rat")
        val namedMattBranch = path("named-matt")
        val namedNatBranch = path("named-nat")
        val namedPath = branchedPath("named") {
            branch(namedMattBranch)
            branch(namedNatBranch)
        }
        val story = story {
            path(onceUponATimePath)
            path(thereWasARatPath)
            path(namedPath)
        }
        assertContentEquals(listOf(onceUponATimePath, thereWasARatPath, namedPath), story.paths)
    }

    @Test
    fun `GIVEN a path WHEN trying to find it THEN it is the current one`() {
        val path = path("preface")
        val story = story { path(path) }
        story.next(path.id)
        assertCurrentPathEquals(path, story)
    }

    @Test
    fun `GIVEN nested paths WHEN trying to find them THEN they're the current one`() {
        val childBranch = path("child")
        val parentBranch = branchedPath("parent") { branch(childBranch) }
        val mainPath = branchedPath("main") { branch(parentBranch) }
        val story = story { path(mainPath) }
        story.next(mainPath.id)
        assertCurrentPathEquals(mainPath, story)
        story.next(parentBranch.id)
        assertCurrentPathEquals(parentBranch, story)
        story.next(childBranch.id)
        assertCurrentPathEquals(childBranch, story)
    }

    @Test
    fun `GIVEN an indirect path WHEN trying to find it THEN it throws`() {
        assertFailsWith<IndirectPathException> {
            story {
                path(path("intro"))
                path(path("resolution"))
                path(branchedPath("extras") { branch(path("credits")) })
            }
                .next("credits")
        }
    }

    private fun assertCurrentPathEquals(expected: Path?, story: Story) {
        assertEquals(expected?.id, story.getCurrentPathFlow().value?.id)
    }
}