package com.jeanbarrossilva.gangame.story.path.branched

import com.jeanbarrossilva.gangame.story.path.path
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class BranchedPathTests {
    @Test
    fun `GIVEN a branch WHEN checking if it is in the path it is`() {
        val branch = path("branch")
        val path = branchedPath("first-branched-path") {
            branch(
                branchedPath("second-branched-path") {
                    branch(branch)
                }
            )
        }
        assertTrue(branch.id in path)
    }

    @Test
    fun `GIVEN a branch WHEN trying to find it THEN it is found`() {
        val branch = path("branch")
        val path = branchedPath("branched-path") { branch(branch) }
        assertNotNull(path.next(branch.id))
    }

    @Test
    fun `GIVEN a nested branch WHEN trying to find it THEN it is found`() {
        val nestedBranch = path("nested-branch")
        val path = branchedPath("main") {
            branch(
                branchedPath("parent") {
                    branch(nestedBranch)
                }
            )
        }
        assertNotNull(path.next(nestedBranch.id))
    }

    @Test
    fun `GIVEN a nonexistent branch WHEN trying to find it THEN it is null`() {
        val path = branchedPath("branched-path") {
            branch(path("first-branch"))
            branch(path("second-branch"))
            branch(path("third-branch"))
        }
        assertNull(path.next("fourth-branch"))
    }
}