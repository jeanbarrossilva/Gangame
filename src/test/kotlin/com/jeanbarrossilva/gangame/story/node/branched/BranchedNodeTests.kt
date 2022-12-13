package com.jeanbarrossilva.gangame.story.node.branched

import com.jeanbarrossilva.gangame.story.node.node
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class BranchedNodeTests {
    @Test
    fun `GIVEN a branch WHEN checking if it is in the node THEN it is`() {
        val branch = node("branch")
        val node = branchedNode("first-branched-node") {
            branch(
                branchedNode("second-branched-node") {
                    branch(branch)
                }
            )
        }
        assertTrue(branch.id in node)
    }

    @Test
    fun `GIVEN a branch WHEN trying to find it THEN it is found`() {
        val branch = node("branch")
        val node = branchedNode("branched-node") { branch(branch) }
        assertNotNull(node.next(branch.id))
    }

    @Test
    fun `GIVEN a nested branch WHEN trying to find it THEN it is found`() {
        val nestedBranch = node("nested-branch")
        val node = branchedNode("main") {
            branch(
                branchedNode("parent") {
                    branch(nestedBranch)
                }
            )
        }
        assertNotNull(node.next(nestedBranch.id))
    }

    @Test
    fun `GIVEN a nonexistent branch WHEN trying to find it THEN it is null`() {
        val node = branchedNode("branched-node") {
            branch(node("first-branch"))
            branch(node("second-branch"))
            branch(node("third-branch"))
        }
        assertNull(node.next("fourth-branch"))
    }
}