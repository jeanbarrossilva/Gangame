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
    fun `GIVEN a path WHEN advancing to it THEN it is the current one`() {
        val path = path("preface")
        val story = story { path(path) }
        story.advanceTo(path.id)
        assertCurrentPathEquals(path, story)
    }

    @Test
    fun `GIVEN nested paths WHEN advancing to them THEN they're the current one`() {
        val childBranch = path("child")
        val parentBranch = branchedPath("parent") { branch(childBranch) }
        val mainPath = branchedPath("main") { branch(parentBranch) }
        val story = story { path(mainPath) }
        story.advanceTo(mainPath.id)
        assertCurrentPathEquals(mainPath, story)
        story.advanceTo(parentBranch.id)
        assertCurrentPathEquals(parentBranch, story)
        story.advanceTo(childBranch.id)
        assertCurrentPathEquals(childBranch, story)
    }

    @Test
    fun `GIVEN a past path WHEN advancing to it THEN it throws`() {
        assertFailsWith<PastPathException> {
            val secondPath = path("second")
            val firstPath = branchedPath("first") { branch(secondPath) }
            val story = story { path(firstPath) }
            story.advanceTo(firstPath.id)
            story.advanceTo(secondPath.id)
            story.advanceTo(firstPath.id)
        }
    }

    @Test
    fun `GIVEN an indirect path WHEN advancing to it THEN it throws`() {
        assertFailsWith<IndirectPathException> {
            story {
                path(path("intro"))
                path(path("resolution"))
                path(branchedPath("extras") { branch(path("credits")) })
            }
                .advanceTo("credits")
        }
    }

    private fun assertCurrentPathEquals(expected: Path?, story: Story) {
        assertEquals(expected?.id, story.getCurrentPathFlow().value?.id)
    }
}